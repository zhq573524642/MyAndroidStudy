package com.zhq.exclusivememory.ui.activity.internet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.constant.Constant;
import com.zhq.exclusivememory.ui.activity.login.LoginActivity;
import com.zhq.exclusivememory.ui.activity.web_view.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/22.
 */

public class InternetActivity extends BaseSimpleActivity {
    private static final int HTTPURLCONNECTION = 1;
    private static final int OKHTTP = 2;
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_build_server)
    Button mBtnBuildServer;
    @BindView(R.id.btn_resolve_pull)
    Button mBtnResolvePull;
    @BindView(R.id.btn_resolve_sax)
    Button mBtnResolveSax;
    @BindView(R.id.btn_resolve_json)
    Button mBtnResolveJson;
    @BindView(R.id.btn_resolve_gson)
    Button mBtnResolveGson;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("网络篇");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_internet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_build_server, R.id.btn_httpUrlConnection, R.id.btn_okHttp, R.id.btn_resolve_pull, R.id.btn_resolve_sax, R.id.btn_resolve_json, R.id.btn_resolve_gson})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_build_server:
                intent.setClass(InternetActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.TITLE_NAME, "搭建Apache服务器");
                intent.putExtra(Constant.WEB_PAGE_URL, API.BUILD_APACHE);
                startActivity(intent);
                break;
            case R.id.btn_httpUrlConnection:
                sendRequestWithHttpUrlConnection();
                break;
            case R.id.btn_okHttp:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient httpClient = new OkHttpClient();
                        Request build = new Request.Builder()
                                .url("http://127.0.0.1/get_data.xml")
                                .get()
                                .build();
                        try {
                            Response response = httpClient.newCall(build).execute();
                            String result = response.body().string();
                            showResponse(OKHTTP, result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                break;
            case R.id.btn_resolve_pull:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient httpClient = new OkHttpClient();
                        Request build = new Request.Builder()
                                .url("http://127.0.0.1/get_data.xml")
                                .get()
                                .build();
                        try {
                            Response response = httpClient.newCall(build).execute();
                            String result = response.body().string();
                            parseXMLWithPull(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_resolve_sax:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient httpClient = new OkHttpClient();
                        Request build = new Request.Builder()
                                .url("http://127.0.0.1/get_data.xml")
                                .get()
                                .build();
                        try {
                            Response response = httpClient.newCall(build).execute();
                            String result = response.body().string();
                            parseXmlWithSAX(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.btn_resolve_json:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient httpClient = new OkHttpClient();
                        Request build = new Request.Builder()
                                .url("http://127.0.0.1/data.json")
                                .get()
                                .build();
                        try {
                            Response response = httpClient.newCall(build).execute();
                            String result = response.body().string();
                            parseJsonWithJsonObject(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_resolve_gson:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient httpClient = new OkHttpClient();
                        Request build = new Request.Builder()
                                .url("http://127.0.0.1/data.json")
                                .get()
                                .build();
                        try {
                            Response response = httpClient.newCall(build).execute();
                            String result = response.body().string();
                            parseJsonWithGson(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    /**
     * 使用Gson进行解析json
     *
     * @param result
     */
    private void parseJsonWithGson(String result) {
        final StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();

        List<GsonParseBean> list = gson.fromJson(result, new TypeToken<List<GsonParseBean>>() {
        }.getType());
        for (GsonParseBean gsonParseBean : list) {
            sb.append("id= ").append(gsonParseBean.getId()).append(";");
            sb.append("name= ").append(gsonParseBean.getName()).append(";");
            sb.append("version= ").append(gsonParseBean.getVersion()).append(";\n");
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InternetActivity.this, "Gson解析", Toast.LENGTH_SHORT).show();
                mTvResult.setText(sb.toString());
            }
        });
    }

    /**
     * 使用jsonObject解析json
     *
     * @param result
     */
    private void parseJsonWithJsonObject(String result) {
        try {
            final StringBuilder sb = new StringBuilder();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                sb.append("id= ").append(id).append(";name= ").append(name).append(";version= ").append(version + ";").append("\n");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(InternetActivity.this, "json解析", Toast.LENGTH_SHORT).show();
                    mTvResult.setText(sb.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * XML解析-SAX
     *
     * @param result
     */
    private void parseXmlWithSAX(String result) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler contentHandler = new ContentHandler();
            //将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(contentHandler);
            //开始解析
            xmlReader.parse(new InputSource(new StringReader(result)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SAX解析使用的ContentHandler
     */
    public class ContentHandler extends DefaultHandler {

        private StringBuilder mId;
        private StringBuilder mName;
        private StringBuilder mVersion;
        private String nodeName;

        @Override
        public void startDocument() throws SAXException {
            mId = new StringBuilder();
            mName = new StringBuilder();
            mVersion = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //记录当前的节点名称
            nodeName = localName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            //根据当前的节点名判断将内容添加到哪一个StringBuilder对象中
            if ("id".equals(nodeName)) {
                mId.append(ch, start, length);
            } else if ("name".equals(nodeName)) {
                mName.append(ch, start, length);
            } else if ("version".equals(nodeName)) {
                mVersion.append(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("app".equals(localName)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText("id= " + mId.toString().trim() + "name= " + mName.toString().trim() + "version= " + mVersion.toString().trim());

                    }
                });
                Log.i("InternetActivity", "====id= " + mId.toString().trim() + " name= " + mName.toString().trim() + " version= " + mVersion.toString().trim());
                //将StringBuilder清空
                mId.setLength(0);
                mName.setLength(0);
                mVersion.setLength(0);


            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }

    /**
     * XML解析-PULL
     *
     * @param result
     */
    private void parseXMLWithPull(String result) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(result));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            final StringBuilder sb = new StringBuilder();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals((nodeName))) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            final String finalId = id;
                            final String finalName = name;
                            final String finalVersion = version;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String s = sb.append("id= ").append(finalId).append("name= ").append(finalName).append("version= ").append(finalVersion).append("\n").toString();
                                    mTvResult.setText(s);
                                }
                            });

                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用HttpUrlConnection进行网络请求
     */
    private void sendRequestWithHttpUrlConnection() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                StringBuilder response = new StringBuilder();
                try {
                    URL url = new URL("http://127.0.0.1/get_data.xml");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(HTTPURLCONNECTION, response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    private void showResponse(int result_status, final String response) {
        switch (result_status) {
            case HTTPURLCONNECTION:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InternetActivity.this, "HTTPURLConnection请求成功", Toast.LENGTH_SHORT).show();
                        mTvResult.setText(response);
                    }
                });
                break;
            case OKHTTP:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InternetActivity.this, "okHttp请求成功", Toast.LENGTH_SHORT).show();
                        mTvResult.setText(response);
                    }
                });
                break;
        }
    }
}
