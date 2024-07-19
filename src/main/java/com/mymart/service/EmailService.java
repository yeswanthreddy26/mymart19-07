package com.mymart.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mymart.model.OrderItem;
import com.mymart.model.Orders;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private InvoiceService invoiceService;

    public void sendOrderConfirmationEmail(String userEmail, Orders orders) {
        try {
            // Generate PDF invoice and save it to a file
            String pdfPath = invoiceService.generatePdfInvoice(orders);

            // Create a MimeMessage
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(userEmail);
            helper.setSubject("MyMart Order Confirmation - Order #" + orders.getOrderNumber());

            // Build the message body with the tracking link
            StringBuilder emailBody = new StringBuilder();
            emailBody.append("Thank you for your order!\n\nYour order has been successfully placed. Order details:\n\n");
            emailBody.append("Order Number: ").append(orders.getOrderNumber()).append("\n\n");

            // Construct the full tracking link with the domain and port
            String trackingLink = "For More Details And track your order, please follow this link: http://localhost:8080/trackOrder/" + orders.getOrderNumber();
            emailBody.append(trackingLink).append("\n\n");

            emailBody.append("Total Amount: $").append(orders.getTotalAmount()).append("\n\n");

            // Add product details
            emailBody.append("Product Details:\n\n");
            for (OrderItem item : orders.getOrderItems()) {
                emailBody.append("Product Name: ").append(item.getProduct().getName()).append("\n");
                emailBody.append("Quantity: ").append(item.getQuantity()).append("\n");
                emailBody.append("Price: $").append(item.getProduct().getPrice()).append("\n");
                emailBody.append("Total Price: $").append(item.getTotalPrice()).append("\n\n");
            }

            emailBody.append("Thank you for shopping with us!\n\nMyMart Team");

            helper.setText(emailBody.toString());

            // Attach the PDF invoice
            File pdfFile = new File(pdfPath);
            helper.addAttachment("Invoice.pdf", pdfFile);

            // Send the email
            emailSender.send(message);

        } catch (MessagingException e) {
            // Handle error
            e.printStackTrace();
        }
    }
}