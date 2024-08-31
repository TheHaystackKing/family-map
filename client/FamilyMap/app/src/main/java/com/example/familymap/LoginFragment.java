package com.example.familymap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Person;
import request.LoginRequest;
import request.RegisterRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonsResponse;
import response.RegisterResponse;


public class LoginFragment extends Fragment {

    private Listener listener;

    public interface Listener {
        void notifyDone();
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    private static final String SUCCESS_KEY = "SuccessKey";

    private Button loginButton;
    private Button registerButton;
    private EditText serverAddress;
    private EditText port;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioGroup gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.loginButton = view.findViewById(R.id.loginButton);
        loginButton.setEnabled(false);
        this.registerButton = view.findViewById(R.id.registerButton);
        registerButton.setEnabled(false);
        serverAddress = view.findViewById(R.id.serverHostField);
        port = view.findViewById(R.id.portField);
        firstName = view.findViewById(R.id.firstNameField);
        lastName = view.findViewById(R.id.lastNameField);
        username = view.findViewById(R.id.usernameField);
        password = view.findViewById(R.id.passwordField);
        email = view.findViewById(R.id.emailField);
        gender = view.findViewById(R.id.genderButtons);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerProxy server = new ServerProxy(serverAddress.getText().toString(), port.getText().toString());
                Handler uiThreadMessageHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();
                        boolean success = bundle.getBoolean(SUCCESS_KEY, false);

                        if(success) {
                            DataCache dataCache = DataCache.getInstance();
                            Handler uiThreadMessageHandlerInner = new Handler() {
                                @Override
                                public void handleMessage(Message message) {
                                    Bundle bundle = message.getData();
                                    boolean success = bundle.getBoolean(SUCCESS_KEY, false);
                                    if(success) {
                                        Person user = dataCache.getUser();
                                        String fullName = user.getFirstName() + " " + user.getLastName();
                                        if(listener != null) {
                                            listener.notifyDone();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Error occurred while retrieving data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            GetDataTask task = new GetDataTask(uiThreadMessageHandlerInner, server, dataCache.getAuthToken());
                            ExecutorService executor = Executors.newSingleThreadExecutor();
                            executor.submit(task);
                        }
                        else {
                            Toast.makeText(getActivity(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
                LoginTask task = new LoginTask(uiThreadMessageHandler, server, request);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(task);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerProxy server = new ServerProxy(serverAddress.getText().toString(), port.getText().toString());
                Handler uiThreadMessageHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();
                        boolean success = bundle.getBoolean(SUCCESS_KEY, false);

                        if(success) {
                            DataCache dataCache = DataCache.getInstance();
                            Handler uiThreadMessageHandlerInner = new Handler() {
                                @Override
                                public void handleMessage(Message message) {
                                    Bundle bundle = message.getData();
                                    boolean success = bundle.getBoolean(SUCCESS_KEY, false);
                                    if(success) {
                                        Person user = dataCache.getUser();
                                        String fullName = user.getFirstName() + " " + user.getLastName();
                                        if(listener != null) {
                                            listener.notifyDone();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Error occurred while retrieving data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            GetDataTask task = new GetDataTask(uiThreadMessageHandlerInner, server, dataCache.getAuthToken());
                            ExecutorService executor = Executors.newSingleThreadExecutor();
                            executor.submit(task);
                        }
                        else {
                            Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                RadioButton genderButton = (RadioButton) gender.findViewById(gender.getCheckedRadioButtonId());
                RegisterRequest request = new RegisterRequest(username.getText().toString(), password.getText().toString(), email.getText().toString(),
                        firstName.getText().toString(), lastName.getText().toString(), genderButton.getText().toString());
                RegisterTask task = new RegisterTask(uiThreadMessageHandler, server, request);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(task);
            }
        });

        serverAddress.addTextChangedListener(loginTextWatcher);
        port.addTextChangedListener(loginTextWatcher);
        username.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);
        firstName.addTextChangedListener(loginTextWatcher);
        lastName.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkTextFields();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkTextFields();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkTextFields() {
        String usernameInput = username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        String serverAddressInput = serverAddress.getText().toString().trim();
        String portInput = port.getText().toString().trim();
        String firstNameInput = firstName.getText().toString().trim();
        String lastNameInput = lastName.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        if(!usernameInput.isEmpty() && !passwordInput.isEmpty() && !serverAddressInput.isEmpty() && !portInput.isEmpty()) {
            loginButton.setEnabled(true);
        }
        else {
            loginButton.setEnabled(false);
        }
        if(!usernameInput.isEmpty() && !passwordInput.isEmpty() && !serverAddressInput.isEmpty() && !portInput.isEmpty()
                && !firstNameInput.isEmpty() && !lastNameInput.isEmpty() && !emailInput.isEmpty()
                && gender.getCheckedRadioButtonId() != -1) {
            registerButton.setEnabled(true);
        }
        else {
            registerButton.setEnabled(false);
        }
    }

    private static class GetDataTask implements Runnable {
        private final Handler messageHandler;
        private final ServerProxy server;
        private final String authToken;

        public GetDataTask(Handler messageHandler, ServerProxy server, String authToken) {
            this.messageHandler = messageHandler;
            this.server = server;
            this.authToken = authToken;
        }

        @Override
        public void run() {
            PersonsResponse people = server.persons(authToken);
            EventsResponse events = server.events(authToken);
            if(people.isSuccess() && events.isSuccess()) {
                DataCache dataCache = DataCache.getInstance();
                dataCache.setPeople(people.getData());
                dataCache.setEvents(events.getData());
                dataCache.setUser(people.getData()[0]);
                dataCache.partitionFamily();
                dataCache.partitionEvents();
                dataCache.organizeData();
                dataCache.setInEventActivity(false);
                dataCache.resetSettings();
                sendMessage(true);
            }
            else {
                sendMessage(false);
            }

        }

        private void sendMessage(boolean success) {
            Message message = Message.obtain();
            Bundle messageBundle = new Bundle();
            messageBundle.putBoolean(SUCCESS_KEY, success);
            message.setData(messageBundle);
            messageHandler.sendMessage(message);
        }
    }

    private static class RegisterTask implements Runnable {
        private final Handler messageHandler;
        private final ServerProxy server;
        private final RegisterRequest request;

        public RegisterTask(Handler messageHandler, ServerProxy server, RegisterRequest request) {
            this.messageHandler = messageHandler;
            this.server = server;
            this.request = request;
        }

        @Override
        public void run() {
            RegisterResponse response = server.register(request);
            if(response.isSuccess()) {
                DataCache dataCache = DataCache.getInstance();
                dataCache.setAuthToken(response.getAuthtoken());
                sendMessage(response.isSuccess());
            }
            else {
                sendMessage(response.isSuccess());
            }

        }

        private void sendMessage(boolean success) {
            Message message = Message.obtain();
            Bundle messageBundle = new Bundle();
            messageBundle.putBoolean(SUCCESS_KEY, success);
            message.setData(messageBundle);
            messageHandler.sendMessage(message);
        }
    }

    private static class LoginTask implements Runnable {

        private final Handler messageHandler;
        private final ServerProxy server;
        private final LoginRequest request;

        public LoginTask(Handler messageHandler, ServerProxy server, LoginRequest request) {
            this.messageHandler = messageHandler;
            this.server = server;
            this.request = request;
        }

        @Override
        public void run() {
            LoginResponse response = server.login(request);
            if (response.isSuccess()) {
                DataCache dataCache = DataCache.getInstance();
                dataCache.setAuthToken(response.getAuthtoken());
                sendMessage(response.isSuccess());
            } else {
                sendMessage(response.isSuccess());
            }
        }

        private void sendMessage(boolean success) {
            Message message = Message.obtain();
            Bundle messageBundle = new Bundle();
            messageBundle.putBoolean(SUCCESS_KEY, success);
            message.setData(messageBundle);
            messageHandler.sendMessage(message);
        }
    }
}