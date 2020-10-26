package com.example.application;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class RemoteControl extends AppCompatActivity
{
    static  String USERNAME ="";
    static  String PASSWORD ="";
    String topic = "Lock/control";
    String message = "";
    MqttAndroidClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        String clientId = MqttClient.generateClientId();
        client =  new MqttAndroidClient(this.getApplicationContext(), "tcp://mqtt.eclipse.org:1883", clientId);
        MqttConnectOptions options = new MqttConnectOptions();


        try {
           // IMqttToken token = client.connect();
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                 //   Log.d(TAG, "onSuccess");
                    Toast toast = Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_LONG);
                    toast.show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                  //  Log.d(TAG, "onFailure");
                    Toast toast = Toast.makeText(getApplicationContext(), "connection failed", Toast.LENGTH_LONG);
                    toast.show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void openDoor(View view)
    {
        TextView textView = (TextView) findViewById(R.id.status_id);
        message="open";
        publishingClient (message);
        textView.clearComposingText();
        textView.setText("Door Open");

    }

    public void closeDoor(View view)
    {
        TextView textView = (TextView) findViewById(R.id.status_id);
        message="close";
        publishingClient (message);
        textView.clearComposingText();
        textView.setText("Door Closed");
    }


    public void publishingClient (String payload)
    {

        try
        {

            client.publish(topic, message.getBytes(),0,false);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }


}