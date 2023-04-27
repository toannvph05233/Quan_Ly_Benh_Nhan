package qlsv_swing.qlsv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import qlsv_swing.qlsv.func.UserFunc;
import qlsv_swing.qlsv.entity.User;
import qlsv_swing.qlsv.view.LoginView;
import qlsv_swing.qlsv.view.RegisterView;
import qlsv_swing.qlsv.view.CustomerView;

public class LoginController {
    private UserFunc userDao;
    private LoginView loginView;
    private RegisterView registerView;
    private CustomerView customerView;
    
    public LoginController(LoginView view, RegisterView registerView) {
        this.loginView = view;
        this.registerView = registerView;
        this.userDao = new UserFunc();
        view.addLoginListener(new LoginListener());
        view.showRegisterView(new RegisterListener());
    }
    
    public void showLoginView() {
        loginView.setVisible(true);
    }
    
    /**
     * Lớp LoginListener chứa cài đặt cho sự kiện click button "Login"
     * 
     * @author viettuts.vn
     */
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                // nếu đăng nhập thành công, mở màn hình quản lý sinh viên
                customerView = new CustomerView();
                CustomerController customerController = new CustomerController(customerView);
                customerController.showStudentView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loginView.setVisible(false);
            registerView.setVisible(true);
        }
    }
}