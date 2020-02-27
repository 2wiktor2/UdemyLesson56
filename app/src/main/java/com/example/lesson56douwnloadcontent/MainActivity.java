package com.example.lesson56douwnloadcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String mailRu = "https://mail.ru/";
    private String triDDDRu = "https://3ddd.ru/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("qwerty", mailRu);
        Log.i("qwerty", triDDDRu);

        DownloadTask task = new DownloadTask();
        try {
            String result = task.execute(mailRu, "https://3ddd.ru/").get();
            Log.i("qwerty", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

    // класс для выполнения кода в другом потоке
    // доступ к нему нам нужен только из этого класса
    private static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.i("qwerty", strings[0]);
            StringBuilder result = new StringBuilder();
            URL url = null;  // обект похож на строку, только должен придерживаться опредленной сигнатуры, представляет собой адрес в интернете (https://3ddd.ru/)
            HttpURLConnection urlConnection = null; // данный объект можно представлять в виде браузера, который открывает наш url и берет оттуда данные

            //  url = new URL(strings[0]);  --- начало
            try {
                // передаем туда нашу ссылку ( url = new URL(strings[0]);). Если ошибиться в строке с адресом(строка не сможет преобразоваться в адрес), то произойдет исключение,
                // поэтому его нужно обернуть в try/catch
                url = new URL(strings[0]);
                // Второе что нужно сделать - открыть соединение
                urlConnection = (HttpURLConnection) url.openConnection(); // и здесь может вылезти исключение( urlConnection = url.openConnection();) поэтому оборачиваем в еще один catch. Вываливается еще одна ощибка в которой нужно преобразовать urlConnection в HttpURLConnection
                // для того чтобы читать данные из интернета, нужно создать поток ввода для того что бы читать данные из этого соединения
                InputStream in = urlConnection.getInputStream();
                // для того чтобы начать процесс чтения этих данных нам нужно создать , создаем его ез нашего InputStream-а in.
                InputStreamReader reader = new InputStreamReader(in); // InputStreamReader создан теперь можно читать данные.
                //reader.read(); // Читает данные по одному символу, что занимает довольно много времени
                // чтобы читать данные сразу строками необходимо создать
                BufferedReader bufferedReader = new BufferedReader(reader);
                // создаем строку
                String line = bufferedReader.readLine();
                while (line != null){ // пока все данные не будут прочитаны
                    result.append(line); // собираем все строки в StringBuilder
                    line = bufferedReader.readLine(); // переменной line присваиваем значение следующей строки
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // независимо от тего успешное было соединение или нет, необходимо закрыть соединение
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }

            return result.toString();
        }
    }
}
