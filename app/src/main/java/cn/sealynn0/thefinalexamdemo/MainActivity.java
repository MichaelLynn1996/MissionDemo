package cn.sealynn0.thefinalexamdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by SeaLynn0 on 2017/10/16.
 */

public class MainActivity extends AppCompatActivity {

    ListView myList;
    EditText et;
    Button submit;
    SimpleAdapter simpleAdapter;
    ArrayList<HashMap<String, Object>> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();

        simpleAdapter = new SimpleAdapter(this, mData, R.layout.item,
                new String[]{"original", "transform", "serial"},
                new int[]{R.id.originalString, R.id.transformString, R.id.serialNumber});
        myList.setAdapter(simpleAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText().length() != 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("original", et.getText().toString());
                    map.put("transform", transform(et.getText().toString()));
                    map.put("serial", mData.size() + 1);
                    mData.add(map);
                    et.setText("");
                }
            }

            //提取字符串中的数字相加，并把结果转换为二进制的方法
            private String transform(String text) { 

                char[] arr = text.toCharArray();
                StringBuilder expression = new StringBuilder();
                String result = "";
                int sum = 0;
                boolean containsNumber = false;

                for (int i = 0; i < arr.length; i++) {
                    if (Character.isDigit(arr[i])) {
                        if (containsNumber)
                            expression.append("+");

                        expression.append(arr[i]);
                        containsNumber = true;
                        sum += Integer.parseInt(String.valueOf(arr[i]));
                        result = expression.toString() + "=" + sum;
                    }
                }
                if (!result.isEmpty())
                    result = result + "\n" +
//                            Integer.toBinaryString(sum);      //调用Java Integer类中写好的方法
                            binaryToDecimal(sum);       //调用自己写的方法
                return result;
            }

            //自己写二进制的转换代码
            private String binaryToDecimal(int n) {
                String str = "";
                while (n != 0) {
                    str = n % 2 + str;
                    n = n / 2;
                }
                return str;
            }
        });

    }

    private void findViewById() {
        myList = (ListView) findViewById(R.id.my_list);
        submit = (Button) findViewById(R.id.bt_submit);
        et = (EditText) findViewById(R.id.et_input);
    }


}
