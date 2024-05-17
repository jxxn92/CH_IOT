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

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 10; // 블루투스 활성화 상태
    private BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    private Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    private BluetoothDevice bluetoothDevice; // 블루투스 디바이스
    private BluetoothSocket bluetoothSocket = null; //블루투스 소켓
    private OutputStream outputStream = null; //블루투스에 데이터를 출력하기 위한 출력 스트림
    private InputStream inputStream = null; //블루투스에 데이터를 입력하기 위한 입력 스트림
    private Thread workerThread = null; //문자열 수신에 사용되는 쓰레드
    private byte[] readBuffer; //수신된 문자열 저장 버퍼
    private int readBufferPosition; //버퍼  내 문자 저장 위치
    String[] array = {"0"};

    private TextView textviewConnectDevice;
    private TextView textviewState;
    private TextView textviewEval;

    private TextView textviewAlcoholText;
    private BackPressCloseHandler backPressCloseHandler;

    private ImageView nowState;

    boolean connectStatus;
    int pairedDeviceCount; //페어링 된 기기의 크기를 저장할 변수
    double maxAlcoholContent = -1.00;
    double alcoholContent;
    LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textviewConnectDevice = findViewById(R.id.connectName);
        textviewState = findViewById(R.id.state);
        textviewAlcoholText = findViewById(R.id.alcoholText);

        back = findViewById(R.id.background);
        nowState = findViewById(R.id.nowState);
        textviewEval = findViewById(R.id.state_eval);

        String deviceName = null;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //블루투스 어댑터를 디폴트 어댑터로 설정

        if (bluetoothAdapter == null) { //기기가 블루투스를 지원하지 않을때
            Toast.makeText(getApplicationContext(), "Bluetooth 미지원 기기입니다.", Toast.LENGTH_SHORT).show();
        } else { // 기기가 블루투스를 지원할 때
            if (bluetoothAdapter.isEnabled()) { // 기기의 블루투스 기능이 켜져있을 경우
                selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출
            } else { // 기기의 블루투스 기능이 꺼져있을 경우
                // 블루투스를 활성화 하기 위한 대화상자 출력
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 선택 값이 onActivityResult함수에서 콜백
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivityForResult(intent, REQUEST_ENABLE_BT);
                selectBluetoothDevice();
            }

        }


    }

    public void selectBluetoothDevice() {
        //이미 페어링 되어있는 블루투스 기기를 탐색
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        devices = bluetoothAdapter.getBondedDevices();
        //페어링 된 디바이스 크기 저장
        pairedDeviceCount = devices.size();
        //페어링 된 장치가 없는 경우
        if (pairedDeviceCount == 0) {
            //페어링 하기 위한 함수 호출
            Toast.makeText(getApplicationContext(), "먼저 Bluetooth 설정에 들어가 페어링을 진행해 주세요.", Toast.LENGTH_SHORT).show();
        }
        //페어링 되어있는 장치가 있는 경우
        else {
            //디바이스를 선택하기 위한 대화상자 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("페어링 된 블루투스 디바이스 목록");
            //페어링 된 각각의 디바이스의 이름과 주소를 저장
            List<String> list = new ArrayList<>();
            //모든 디바이스의 이름을 리스트에 추가
            for (BluetoothDevice bluetoothDevice : devices) {
                list.add(bluetoothDevice.getName());
            }
            list.add("취소");

            //list를 Charsequence 배열로 변경
            final CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);
            list.toArray(new CharSequence[list.size()]);

            //해당 항목을 눌렀을 때 호출되는 이벤트 리스너
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //해당 디바이스와 연결하는 함수 호출
                    connectDevice(charSequences[which].toString());
                }
            });
            //뒤로가기 버튼 누를때 창이 안닫히도록 설정
            builder.setCancelable(false);
            //다이얼로그 생성
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
        //페어링 된 디바이스 모두 탐색
        for (BluetoothDevice bluetoothDevice1 : devices) {
            //사용자가 선택한 이름과 같은 디바이스로 설정하고 반복문 종료
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (deviceName.equals(bluetoothDevice1.getName())) {
                bluetoothDevice = bluetoothDevice1;
                break;
            }

        }
        Toast.makeText(getApplicationContext(), bluetoothDevice.getName() + " 연결 완료!", Toast.LENGTH_SHORT).show();
        //UUID생성
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        connectStatus = true;
        //Rfcomm 채널을 통해 블루투스 디바이스와 통신하는 소켓 생성

        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();
            receiveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //상단에 연결된 디바이스 이름을 출력
        textviewConnectDevice.setText(bluetoothDevice.getName());
        //배경색 변경
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
                                            double receivedValue = parseDouble(array[0]);
                                            if (receivedValue > maxAlcoholContent) {
                                                maxAlcoholContent = receivedValue;
                                            }

                                            textviewAlcoholText.setText(array[0]);
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