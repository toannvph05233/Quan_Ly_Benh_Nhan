package qlsv_swing.qlsv.controller;

import qlsv_swing.qlsv.entity.CustomerID;
import qlsv_swing.qlsv.entity.Diagnose;
import qlsv_swing.qlsv.entity.Prescription;
import qlsv_swing.qlsv.func.CustomerFunc;
import qlsv_swing.qlsv.view.CustomerView;
import qlsv_swing.qlsv.view.DiagnoseView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DiagnoseController {
    private CustomerFunc customerFunc;
    private CustomerView customerView;
    private DiagnoseView diagnoseView;

    public DiagnoseController(CustomerView view, CustomerFunc customerFunc,DiagnoseView diagnoseView ) {
        this.customerView = view;
        this.customerFunc = customerFunc;
        this.diagnoseView = diagnoseView;
        diagnoseView.addListDiagnoseSelectionListener(new ListDiagnoseSelectionListener() );
        diagnoseView.addListPrescriptionSelectionListener(new ListPrescriptionSelectionListener());
        diagnoseView.addAddDiagnoseListener(new AddDiagnoseListener());
        diagnoseView.addClearDiagnoseListener(new ClearDiagnoseListener());
        diagnoseView.addEditDiagnoseListener(new EditDiagnoseListener());
        diagnoseView.addDeleteDiagnoseListener(new DeleteDiagnoseListener());
        diagnoseView.backDiagnoseListener(new BackDiagnoseListener());
        diagnoseView.addPrescriptionListener(new AddPrescriptionListener());
        diagnoseView.editPrescriptionListener(new EditPrescriptionListener());
        diagnoseView.deletePrescriptionListener(new DeletePrescriptionListener());
        diagnoseView.clearPrescriptionListener(new ClearPrescriptionListener());

    }
    public void showDiagnoses(){
        int idCustomer = customerView.getIdCustomer();
        CustomerID.idCustomer = idCustomer;
        CustomerID.name = customerView.getNameCustomer();
        diagnoseView.setVisible(true);
        diagnoseView.showListDiagnoses(customerFunc.getDiagnoseById(idCustomer));
    }

    class ListDiagnoseSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            diagnoseView.fillDiagnoseFromSelectedRow();
        }
    }
    class ListPrescriptionSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            diagnoseView.fillPrescriptionFromSelectedRow();
        }
    }

    class AddDiagnoseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Diagnose diagnose = diagnoseView.getDiagnoseInfo();
            if (diagnose != null) {
                List<Diagnose> diagnoses = customerFunc.getDiagnoseById(CustomerID.idCustomer);
                if (diagnoses.size() > 0) {
                    diagnose.setId(diagnoses.get(diagnoses.size() - 1).getId() + 1);
                } else {
                    diagnose.setId(1);
                }
                customerFunc.addDiagnose(diagnose);
                diagnoseView.showListDiagnoses(customerFunc.getDiagnoseById(CustomerID.idCustomer));
                customerView.showMessage("Thêm thành công!");
            }
        }
    }

    class AddPrescriptionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Diagnose diagnose = diagnoseView.getDiagnoseInfo();
            Prescription prescription = diagnoseView.getPrescriptionInfo();
            if (prescription != null) {
                List<Prescription> prescriptions = customerFunc.getPresByIdCustomer(CustomerID.idCustomer,diagnose.getId());
                if (prescriptions.size() > 0) {
                    prescription.setId(prescriptions.get(prescriptions.size() - 1).getId() + 1);
                } else {
                    prescription.setId(1);
                }
                customerFunc.addPrescription(prescription, diagnose.getId());
                diagnoseView.showListPres(customerFunc.getPresByIdCustomer(CustomerID.idCustomer, diagnose.getId()));
                customerView.showMessage("Thêm thành công!");
            }
        }
    }

    class EditDiagnoseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Diagnose diagnose = diagnoseView.getDiagnoseInfo();
            if (diagnose != null) {
                customerFunc.editDiagnose(diagnose, diagnose.getId());
                diagnoseView.showListDiagnoses(customerFunc.getDiagnoseById(CustomerID.idCustomer));
                customerView.showMessage("Sửa thành công!");
            }
        }
    }

    class EditPrescriptionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prescription prescription = diagnoseView.getPrescriptionInfo();
            Diagnose diagnose = diagnoseView.getDiagnoseInfo();
            if (prescription != null) {
                customerFunc.editPrescription(prescription, diagnose.getId());
                diagnoseView.showListPres(customerFunc.getPresByIdCustomer(CustomerID.idCustomer, diagnose.getId()));
                customerView.showMessage("Sửa thành công!");
            }
        }
    }
    class ClearDiagnoseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            diagnoseView.clearDiagnoseInfo();
        }
    }

    class ClearPrescriptionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            diagnoseView.clearPrescriptionInfo();
        }
    }

    class BackDiagnoseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            diagnoseView.setVisible(false);
            customerView.setVisible(true);
        }
    }


    class DeleteDiagnoseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Diagnose diagnose = diagnoseView.getDiagnoseInfo();
            if (diagnose != null) {
                customerFunc.deleteDiagnose(diagnose.getId());
                diagnoseView.showListDiagnoses(customerFunc.getDiagnoseById(CustomerID.idCustomer));
                customerView.showMessage("Xóa thành công!");
            }
        }
    }
    class DeletePrescriptionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prescription prescription = diagnoseView.getPrescriptionInfo();
            Diagnose diagnose = diagnoseView.getDiagnoseInfo();
            if (prescription != null) {
                customerFunc.deletePrescription(prescription.getId(),diagnose.getId());
                diagnoseView.showListPres(customerFunc.getPresByIdCustomer(CustomerID.idCustomer, diagnose.getId()));
                customerView.showMessage("Xóa thành công!");
            }
        }
    }
}