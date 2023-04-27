package qlsv_swing.qlsv.func;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import qlsv_swing.qlsv.entity.*;
import qlsv_swing.qlsv.utils.FileUtils;

/**
 * StudentFunc class
 *
 * @author viettuts.vn
 */
public class CustomerFunc {
    private static final String STUDENT_FILE_NAME = "customer.xml";
    private List<Customer> listCustomers;

    public CustomerFunc() {
        this.listCustomers = readListCustomers();
    }

    /**
     * Lưu các đối tượng student vào file student.xml
     *
     * @param customers
     */
    public void writeListCustomers(List<Customer> customers) {
        CustomerXML customerXML = new CustomerXML();
        customerXML.setStudent(customers);
        FileUtils.writeXMLtoFile(STUDENT_FILE_NAME, customerXML);
    }

    /**
     * Đọc các đối tượng student từ file student.xml
     *
     * @return list student
     */
    public List<Customer> readListCustomers() {
        List<Customer> list = new ArrayList<Customer>();
        CustomerXML customerXML = (CustomerXML) FileUtils.readXMLFile(
                STUDENT_FILE_NAME, CustomerXML.class);
        if (customerXML != null) {
            list = customerXML.getStudent();
        }
        return list;
    }


    /**
     * thêm student vào listStudents và lưu listStudents vào file
     *
     * @param customer
     */
    public void add(Customer customer) {
        int id = (listCustomers.size() > 0) ? (listCustomers.size() + 1) : 1;
        customer.setId(id);
        listCustomers.add(customer);
        writeListCustomers(listCustomers);
    }

    public void addDiagnose(int idCustomer, Diagnose diagnose) {
        Customer customer = findById(idCustomer);
        int id = (customer.getDiagnoses().size() > 0) ? (customer.getDiagnoses().size() + 1) : 1;
        diagnose.setId(id);
        customer.getDiagnoses().add(diagnose);
        writeListCustomers(listCustomers);
    }

    public void editDiagnose(int idCustomer, Diagnose diagnose) {
        Customer customer = findById(idCustomer);
        List<Diagnose> diagnoses = customer.getDiagnoses();
        int size = diagnoses.size();
        for (int i = 0; i < size; i++) {
            if (diagnoses.get(i).getId() == diagnose.getId()) {
                diagnoses.get(i).setDiagnose(diagnose.getDiagnose());
                writeListCustomers(listCustomers);
                break;
            }
        }
    }

    public boolean deleteDiagnose(int idCustomer, Diagnose diagnose) {
        Customer customer = findById(idCustomer);
        for (int i = 0; i < customer.getDiagnoses().size(); i++) {
            if (customer.getDiagnoses().get(i).getId() == diagnose.getId()) {
                customer.getDiagnoses().remove(i);
            }
        }
        writeListCustomers(listCustomers);
        return true;
    }

    /**
     * cập nhật student vào listStudents và lưu listStudents vào file
     *
     * @param customer
     */
    public void edit(Customer customer) {
        int size = listCustomers.size();
        for (int i = 0; i < size; i++) {
            if (listCustomers.get(i).getId() == customer.getId()) {
                listCustomers.get(i).setName(customer.getName());
                listCustomers.get(i).setAge(customer.getAge());
                listCustomers.get(i).setAddress(customer.getAddress());
                listCustomers.get(i).setPhone(customer.getPhone());
                writeListCustomers(listCustomers);
                break;
            }
        }
    }

    /**
     * xóa student từ listStudents và lưu listStudents vào file
     *
     * @param customer
     */
    public boolean delete(Customer customer) {
        boolean isFound = false;
        int size = listCustomers.size();
        for (int i = 0; i < size; i++) {
            if (listCustomers.get(i).getId() == customer.getId()) {
                customer = listCustomers.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            listCustomers.remove(customer);
            writeListCustomers(listCustomers);
            return true;
        }
        return false;
    }

    public Customer findById(int id) {
        for (Customer c : listCustomers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> findAllByName(String name) {
        List<Customer> customers = new ArrayList<>();
        for (Customer c : listCustomers) {
            if (c.getName().contains(name)) {
                customers.add(c);
            }
        }
        return customers;
    }

    /**
     * sắp xếp danh sách student theo name theo tứ tự tăng dần
     */
    public void sortCustomerByName() {
        Collections.sort(listCustomers, new Comparator<Customer>() {
            public int compare(Customer customer1, Customer customer2) {
                return customer1.getName().compareTo(customer2.getName());
            }
        });
    }

    public List<Prescription> getPresByIdCustomer(int idCustomer, int idPrescription) {
        List<Diagnose> diagnoses = getDiagnoseById(idCustomer);
        for (Diagnose diagnose : diagnoses) {
            if (diagnose.getId() == idPrescription) {
                return diagnose.getPrescriptions();
            }
        }
        return new ArrayList<>();
    }

    public List<Diagnose> getDiagnoseById(int idCustomer) {
        List<Customer> customerList = getListStudents();
        for (Customer c : customerList) {
            if (c.getId() == idCustomer) {
                return c.getDiagnoses();
            }
        }
        return new ArrayList<>();

    }

    public void addDiagnose(Diagnose diagnose) {
        List<Diagnose> diagnoses = getDiagnoseById(CustomerID.idCustomer);
        diagnoses.add(diagnose);
        writeListCustomers(listCustomers);

    }

    public void addPrescription(Prescription prescription, int idDiagnose) {
        List<Diagnose> diagnoses = getDiagnoseById(CustomerID.idCustomer);
        for (Diagnose d : diagnoses) {
            if (d.getId() == idDiagnose) {
                d.getPrescriptions().add(prescription);
            }
        }
        writeListCustomers(listCustomers);

    }

    public void editPrescription(Prescription prescription, int idDiagnose) {
        List<Diagnose> diagnoses = getDiagnoseById(CustomerID.idCustomer);
        for (Diagnose d : diagnoses) {
            if (d.getId() == idDiagnose) {
                for (int i = 0; i < d.getPrescriptions().size(); i++) {
                    if (d.getPrescriptions().get(i).getId() == prescription.getId()) {
                        d.getPrescriptions().set(i, prescription);
                        return;
                    }
                }
            }
        }
        writeListCustomers(listCustomers);
    }

    public void deletePrescription(int idPrescription, int idDiagnose) {
        List<Diagnose> diagnoses = getDiagnoseById(CustomerID.idCustomer);
        for (Diagnose d : diagnoses) {
            if (d.getId() == idDiagnose) {
                for (int i = 0; i < d.getPrescriptions().size(); i++) {
                    if (d.getPrescriptions().get(i).getId() == idPrescription) {
                        d.getPrescriptions().remove(i);
                        return;
                    }
                }
            }
        }
        writeListCustomers(listCustomers);
    }

    public void editDiagnose(Diagnose diagnose, int id) {
        List<Diagnose> diagnoses = getDiagnoseById(CustomerID.idCustomer);
        for (Diagnose d : diagnoses) {
            if (d.getId() == id) {
                d.setDiagnose(diagnose.getDiagnose());
            }
        }
        writeListCustomers(listCustomers);

    }

    public void deleteDiagnose(int id) {
        List<Diagnose> diagnoses = getDiagnoseById(CustomerID.idCustomer);
        for (Diagnose d : diagnoses) {
            if (d.getId() == id) {
                diagnoses.remove(d);
            }
        }
        writeListCustomers(listCustomers);

    }

    public List<Customer> getListStudents() {
        return listCustomers;
    }

    public void setListStudents(List<Customer> listCustomers) {
        this.listCustomers = listCustomers;
    }
}
