package com.example.ch_iot;

import static java.lang.Double.parseDouble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AlcoholActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 10;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> devices;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket = null;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private Thread workerThread = null;
    private byte[] readBuffer;
    private int readBufferPosition;
    String[] array = {"0"};

    private TextView textviewConnectDevice;
    private TextView textviewState;
    private TextView textviewEval;

    private TextView textviewAlcoholText;
    private BackPressCloseHandler backPressCloseHandler;

    private ImageView nowState;

    boolean connectStatus;
    int pairedDeviceCount;
    double maxAlcoholContent = -1.00;
    double alcoholContent = 0.00;

    String alcoholContentStr = "";
    LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alcohol);

        textviewConnectDevice = findViewById(R.id.connectName);
        textviewState = findViewById(R.id.state);
        textviewAlcoholText = findViewById(R.id.alcoholText);

        back = findViewById(R.id.background);
        nowState = findViewById(R.id.nowState);
        textviewEval = findViewById(R.id.state_eval);

        String deviceName = null;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 블루투스 지원 X
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth를 지원하지 않는 기기 입니다.", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                selectBluetoothDevice();
            } else {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivityForResult(intent, REQUEST_ENABLE_BT);
                selectBluetoothDevice();
            }

        }


    }

    public void selectBluetoothDevice() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        devices = bluetoothAdapter.getBondedDevices();
        pairedDeviceCount = devices.size();
        if (pairedDeviceCount == 0) {
            Toast.makeText(getApplicationContext(), "먼저 블루투스 설정에 들어가 페어링을 진행해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("페어링 된 블루투스 디바이스 목록");
            List<String> list = new ArrayList<>();
            for (BluetoothDevice bluetoothDevice : devices) {
                list.add(bluetoothDevice.getName());
            }
            list.add("취소");

            final CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);
            list.toArray(new CharSequence[list.size()]);

            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    connectDevice(charSequences[which].toString());
                }
            });
            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    @Override
    public void onBackPressed() { //뒤로가기 눌렀을때
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed(); //2번누르면 종료
    }

    //연결 함수
    public void connectDevice(String deviceName) {
        for (BluetoothDevice bluetoothDevice1 : devices) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (deviceName.equals(bluetoothDevice1.getName())) {
                bluetoothDevice = bluetoothDevice1;
                break;
            }

        }
        Toast.makeText(getApplicationContext(), bluetoothDevice.getName() + " 연결 완료!", Toast.LENGTH_SHORT).show();
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        connectStatus = true;

        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();
            receiveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        textviewConnectDevice.setText(bluetoothDevice.getName());
        back.setBackgroundResource(R.drawable.gradient_none);
        nowState.setImageResource(R.drawable.none);
        nowState.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        textviewState.setText("");
        textviewEval.setText("");
        textviewAlcoholText.setText("0.00");
    }

    public void receiveData() {
        final Handler handler = new Handler();
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int byteAvailable = inputStream.available();
                        if (byteAvailable > 0) {
                            byte[] bytes = new byte[1024];
                            int read = inputStream.read(bytes);
                            for (int i = 0; i < byteAvailable; i++) {
                                byte tempByte = bytes[i];
                                if (tempByte == '\n') {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String text = new String(encodedBytes, "UTF-8");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            array = text.split(",", 3);
                                            alcoholContent = parseDouble(array[0]);
                                            alcoholContentStr = array[0];
                                            if (alcoholContent > maxAlcoholContent) {
                                                maxAlcoholContent = alcoholContent;
                                            }

                                            // Test
                                            textviewAlcoholText.setText(alcoholContentStr);

                                            // 0.00
                                            if (alcoholContent == 0.00) {
                                                back.setBackgroundResource(R.drawable.gradient_none);
                                                nowState.setImageResource(R.drawable.none);
                                                nowState.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                                textviewAlcoholText.setText(alcoholContentStr);
                                            }
                                            // 0.00초과 ~ 0.02이하
                                            if (alcoholContent > 0.00) {
                                                back.setBackgroundResource(R.drawable.gradient_low);
                                                nowState.setImageResource(R.drawable.low);
                                                nowState.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                                textviewAlcoholText.setText(alcoholContentStr);
                                            }
                                            // 0.02초과 ~ 0.05이하
                                            if (alcoholContent >= 0.02) {
                                                back.setBackgroundResource(R.drawable.gradient_middle);
                                                nowState.setImageResource(R.drawable.middle);
                                                nowState.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                                textviewAlcoholText.setText(alcoholContentStr);
                                            }
                                            // 0.05 초과
                                            if (alcoholContent > 0.05) {
                                                back.setBackgroundResource(R.drawable.gradient_high);
                                                nowState.setImageResource(R.drawable.high);
                                                nowState.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                                textviewAlcoholText.setText(alcoholContentStr);
                                            }

                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = tempByte;
                                }
                            }
                        }
                        Thread.sleep(300);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();
    }
}