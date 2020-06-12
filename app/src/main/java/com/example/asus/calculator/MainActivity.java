package com.example.asus.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //result and other fields:
    private EditText result;
    private EditText newnumber;
    private TextView displayoperation;


    //buttons:
    private Button b0;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button bequal;
    private Button bdiv;
    private Button bmul;
    private Button bdot;
    private Button bsub;
    private Button badd;
    private Button buttonneg;

    //operands:
    private Double operand1=null;
    private Double operand2=null;
    private String pendingoperation="=";
    private final static String STATE_PENDING_OPERATION="pendingoperation";
    private final static String STATE_OPERAND1="operand1";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,pendingoperation);
        if(operand1!=null){
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingoperation =savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1=savedInstanceState.getDouble(STATE_OPERAND1);
        displayoperation.setText(pendingoperation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result =(EditText)findViewById(R.id.result);
        newnumber=(EditText)findViewById(R.id.newnumber);
        displayoperation=(TextView) findViewById(R.id.operation);

        b0=(Button)findViewById(R.id.button0);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button7);
        b8=(Button)findViewById(R.id.button8);
        b9=(Button)findViewById(R.id.button9);
        bdot=(Button)findViewById(R.id.buttonDot);

        bequal=(Button)findViewById(R.id.buttonequal);
        badd=(Button)findViewById(R.id.buttonplus);
        bsub=(Button)findViewById(R.id.buttonsub);
        bmul=(Button)findViewById(R.id.buttonmul);
        bdiv=(Button)findViewById(R.id.buttondiv);


        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b=(Button)v;
                newnumber.append(b.getText().toString());
            }
        };

        buttonneg=(Button)findViewById(R.id.buttonneg);

        View.OnClickListener negListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=newnumber.getText().toString();
                if (value.length()==0){
                    newnumber.setText("-");
                }else{
                    try {
                        Double doublevalue = Double.valueOf(value);
                        doublevalue *= -1;
                        newnumber.setText(doublevalue.toString());
                    }catch (NumberFormatException e){
                        //If already - or . is given:
                        newnumber.setText("");
                    }
                }
            }
        };

        buttonneg.setOnClickListener(negListener);


        b0.setOnClickListener(listener);
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
        b6.setOnClickListener(listener);
        b7.setOnClickListener(listener);
        b8.setOnClickListener(listener);
        b9.setOnClickListener(listener);
        bdot.setOnClickListener(listener);

        View.OnClickListener oplistener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b=(Button)v;
                String op=b.getText().toString();
                String value=newnumber.getText().toString();
                try{
                    Double numdouble=Double.valueOf(value);
                    performOperation(numdouble,op);
                }catch (NumberFormatException e){
                    newnumber.setText("");
                }
                pendingoperation=op;
                displayoperation.setText(pendingoperation);
            }

            private void performOperation(Double value, String op) {
                if(operand1==null){
                    operand1=value;
                }
                else{
                    operand2=Double.valueOf(value);
                    if(pendingoperation.equals("=")){
                        pendingoperation=op;
                    }
                    switch (pendingoperation){
                        case "=":
                            operand1=operand2;
                            break;
                        case "/":
                            if(operand2==0){
                                operand1=0.0;
                            }else {
                                operand1/= operand2;
                            }
                            break;
                        case "*":
                            operand1*=operand2;
                            break;
                        case  "-":
                            operand1-=operand2;
                            break;
                        case "+":
                            operand1+=operand2;
                            break;

                    }
                }
                result.setText(operand1.toString());
                newnumber.setText("");


            }
        };

        bequal.setOnClickListener(oplistener);
        badd.setOnClickListener(oplistener);
        bsub.setOnClickListener(oplistener);
        bmul.setOnClickListener(oplistener);
        bdiv.setOnClickListener(oplistener);







    }
}
