package t0mat0ts.com.a7segsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class MainActivity extends Activity {

    // 7セグメントLEDで使うポート
    private static final String SEG_A = "BCM17";
    private static final String SEG_B = "BCM27";
    private static final String SEG_C = "BCM22";
    private static final String SEG_D = "BCM23";
    private static final String SEG_E = "BCM24";
    private static final String SEG_F = "BCM25";
    private static final String SEG_G = "BCM5";
    private static final String SEG_DP = "BCM6";
    private static final String SEG_FIRST = "BCM21";
    private static final String SEG_SECOND = "BCM20";
    private static final String SEG_THIRD = "BCM16";
    private static final int INTERVAL = 1000;

    private Handler handler;

    // 今回使用するGPIO
    private Gpio segA;
    private Gpio segB;
    private Gpio segC;
    private Gpio segD;
    private Gpio segE;
    private Gpio segF;
    private Gpio segG;
    private Gpio segDp;
    private Gpio segFirst;
    private Gpio segSecond;
    private Gpio segThird;

    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GPIOの初期化
        initGpio();
        handler = new Handler();

        // 表示させたい数字で初期化
        num = 123;

        // 別スレッドで表示処理
        handler.post(runnable);
        thread.start();
    }

    // GPIOの初期化メソッド
    private void initGpio() {
        PeripheralManager manager = PeripheralManager.getInstance();
        try {
            segA = manager.openGpio(SEG_A);
            segB = manager.openGpio(SEG_B);
            segC = manager.openGpio(SEG_C);
            segD = manager.openGpio(SEG_D);
            segE = manager.openGpio(SEG_E);
            segF = manager.openGpio(SEG_F);
            segG = manager.openGpio(SEG_G);
            segDp = manager.openGpio(SEG_DP);
            segFirst = manager.openGpio(SEG_FIRST);
            segSecond = manager.openGpio(SEG_SECOND);
            segThird = manager.openGpio(SEG_THIRD);

            segA.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segB.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segC.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segD.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segE.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segF.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segG.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segDp.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segFirst.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segSecond.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            segThird.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, INTERVAL);
        }
    };

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                segFirst.setValue(false);
                segSecond.setValue(false);
                segThird.setValue(false);
            } catch (Exception e) {
            }
            while (true) {
                if (num > 99) {
                    try {
                        segFirst.setValue(false);
                        segSecond.setValue(false);
                        segThird.setValue(false);
                        int secondDigit = (num % 1000) / 100;
                        showNumber(secondDigit);
                        segFirst.setValue(true);
                        segSecond.setValue(false);
                        segThird.setValue(false);
                        Thread.sleep(1);
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        segFirst.setValue(false);
                        segSecond.setValue(false);
                        segThird.setValue(false);
                        showNumber(0);
                        segFirst.setValue(true);
                        segSecond.setValue(false);
                        segThird.setValue(false);
                        Thread.sleep(1);
                    } catch (Exception e) {
                    }
                }
                if (num > 9) {
                    try {
                        segFirst.setValue(false);
                        segSecond.setValue(false);
                        segThird.setValue(false);
                        int thirdDigit = num % 1000 % 100 / 10;
                        showNumber(thirdDigit);
                        segFirst.setValue(false);
                        segSecond.setValue(true);
                        segThird.setValue(false);
                        Thread.sleep(1);
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        segFirst.setValue(false);
                        segSecond.setValue(false);
                        segThird.setValue(false);
                        showNumber(0);
                        segFirst.setValue(false);
                        segSecond.setValue(true);
                        segThird.setValue(false);
                        Thread.sleep(1);
                    } catch (Exception e) {
                    }
                }
                try {
                    segFirst.setValue(false);
                    segSecond.setValue(false);
                    segThird.setValue(false);
                    int fourthdigit = num % 1000 % 100 % 10;
                    showNumber(fourthdigit);
                    segFirst.setValue(false);
                    segSecond.setValue(false);
                    segThird.setValue(true);
                    Thread.sleep(1);
                } catch (Exception e) {
                }
            }
        }
    });

    private void showNumber(int num){
        try {
            segA.setValue(true);
            segB.setValue(true);
            segC.setValue(true);
            segD.setValue(true);
            segE.setValue(true);
            segF.setValue(true);
            segG.setValue(true);
            segDp.setValue(true);
            switch(num){
                case 0:
                    segA.setValue(false);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(false);
                    segE.setValue(false);
                    segF.setValue(false);
                    segG.setValue(true);
                    segDp.setValue(true);
                    break;
                case 1:
                    segA.setValue(true);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(true);
                    segE.setValue(true);
                    segF.setValue(true);
                    segG.setValue(true);
                    segDp.setValue(true);
                    break;
                case 2:
                    segA.setValue(false);
                    segB.setValue(false);
                    segC.setValue(true);
                    segD.setValue(false);
                    segE.setValue(false);
                    segF.setValue(true);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
                case 3:
                    segA.setValue(false);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(false);
                    segE.setValue(true);
                    segF.setValue(true);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
                case 4:
                    segA.setValue(true);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(true);
                    segE.setValue(true);
                    segF.setValue(false);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
                case 5:
                    segA.setValue(false);
                    segB.setValue(true);
                    segC.setValue(false);
                    segD.setValue(false);
                    segE.setValue(true);
                    segF.setValue(false);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
                case 6:
                    segA.setValue(false);
                    segB.setValue(true);
                    segC.setValue(false);
                    segD.setValue(false);
                    segE.setValue(false);
                    segF.setValue(false);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
                case 7:
                    segA.setValue(false);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(true);
                    segE.setValue(true);
                    segF.setValue(true);
                    segG.setValue(true);
                    segDp.setValue(true);
                    break;
                case 8:
                    segA.setValue(false);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(false);
                    segE.setValue(false);
                    segF.setValue(false);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
                case 9:
                    segA.setValue(false);
                    segB.setValue(false);
                    segC.setValue(false);
                    segD.setValue(false);
                    segE.setValue(true);
                    segF.setValue(false);
                    segG.setValue(false);
                    segDp.setValue(true);
                    break;
            }
        } catch (IOException e) {
        }
    }

}
