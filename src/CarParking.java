import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CarParking {
    private JPanel Main;
    private JTextField txtCReg;
    private JTextField txtPhone;
    private JButton saveButton;
    private JButton deleteButton;
    private JTextField txtbrand;
    private JTextField txtcol;
    private JTextField txtTime;
    private JTextField txtOwnName;
    private JTable table1;
    private JButton updateButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CarParking");
        frame.setContentPane(new CarParking().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rrcomp", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from carparking");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public CarParking() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carRegNum, brand, colour, name;
                String time;
                String phone;

                carRegNum = txtCReg.getText();
                brand = txtbrand.getText();
                colour = txtcol.getText();
                time = txtTime.getText();
                name = txtOwnName.getText();
                phone = txtPhone.getText();

                try {
                    pst = con.prepareStatement("insert into carparking(carRegNum,brand,colour,time,name,phone)values(?,?,?,?,?,?)");
                    pst.setString(1, carRegNum);
                    pst.setString(2, brand);
                    pst.setString(3, colour);
                    pst.setString(4, time);
                    pst.setString(5, name);
                    pst.setString(6, phone);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    table_load();
                    txtCReg.setText("");
                    txtbrand.setText("");
                    txtcol.setText("");
                    txtTime.setText("");
                    txtOwnName.setText("");
                    txtPhone.setText("");
                    txtCReg.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String id = txtid.getText();
                    pst = con.prepareStatement("select carRegNum,brand,colour,time,name,phone from carparking where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String carRegNum = rs.getString(1);
                        String brand = rs.getString(2);
                        String colour = rs.getString(3);
                        String time = rs.getString(4);
                        String name = rs.getString(5);
                        String phone = rs.getString(6);


                        txtCReg.setText(carRegNum);
                        txtbrand.setText(brand);
                        txtcol.setText(colour);
                        txtTime.setText(time);
                        txtOwnName.setText(name);
                        txtPhone.setText(phone);
                    }
                    else
                    {
                        txtCReg.setText("");
                        txtbrand.setText("");
                        txtcol.setText("");
                        txtTime.setText("");
                        txtOwnName.setText("");
                        txtPhone.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Car Number");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carRegNum, brand, colour, time,name,phone, id;
                carRegNum = txtCReg.getText();
                brand = txtbrand.getText();
                colour = txtcol.getText();
                time = txtTime.getText();
                name = txtOwnName.getText();
                phone = txtPhone.getText();
                id = txtid.getText();

                try {
                    pst = con.prepareStatement("update carparking set carRegNum = ?,brand = ?,colour = ?, time = ?, name = ?, phone = ? where id = ?");
                    pst.setString(1, carRegNum);
                    pst.setString(2, brand);
                    pst.setString(3, colour);
                    pst.setString(4, time);
                    pst.setString(5, name);
                    pst.setString(6, phone);
                    pst.setString(7, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update");
                    table_load();
                    txtCReg.setText("");
                    txtbrand.setText("");
                    txtcol.setText("");
                    txtTime.setText("");
                    txtOwnName.setText("");
                    txtPhone.setText("");
                    txtCReg.requestFocus();
                    txtid.setText("");
                }

                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtid.getText();
                try {
                    pst = con.prepareStatement("delete from carparking  where id = ?");
                    pst.setString(1, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtCReg.setText("");
                    txtbrand.setText("");
                    txtcol.setText("");
                    txtTime.setText("");
                    txtOwnName.setText("");
                    txtPhone.setText("");
                    txtCReg.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
