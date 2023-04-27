package qlsv_swing.qlsv.controller;

import qlsv_swing.qlsv.entity.User;
import qlsv_swing.qlsv.func.UserFunc;
import qlsv_swing.qlsv.view.LoginView;
import qlsv_swing.qlsv.view.RegisterView;
import qlsv_swing.qlsv.view.CustomerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {
    private UserFunc userDao;
    private LoginView loginView;
    private RegisterView registerView;

    private CustomerView customerView;
    public RegisterController(LoginView view, RegisterView registerView) {
        this.loginView = view;
        this.registerView = registerView;
        this.userDao = new UserFunc();
        registerView.addRegisterListener(new RegisterListener());
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = registerView.getUser();
            if (userDao.checkUserName(user)) {
                userDao.add(user);
                loginView.setVisible(true);
                registerView.setVisible(false);
            } else {
                registerView.showMessage("username đang bị trùng hoặc để trống");
            }
        }
    }
}
