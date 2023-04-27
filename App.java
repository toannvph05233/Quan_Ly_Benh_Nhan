package qlsv_swing.qlsv;

import java.awt.EventQueue;

import qlsv_swing.qlsv.controller.LoginController;
import qlsv_swing.qlsv.controller.RegisterController;
import qlsv_swing.qlsv.view.LoginView;
import qlsv_swing.qlsv.view.RegisterView;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                RegisterView registerView = new RegisterView();
                LoginController controller = new LoginController(view, registerView);
                RegisterController registerController = new RegisterController(view,registerView);
                // hiển thị màn hình login
                controller.showLoginView();
            }
        });
    }
}