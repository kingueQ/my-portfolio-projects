# 🗂️ Asset Management System

This project is a web application developed to manage asset-related requests within a company. It integrates with the osTicket platform and was built using Node.js and MySQL, with complementary use of HTML, CSS, and JavaScript.

## 📌 Overview

The system automatically detects when a ticket in osTicket changes its help topic. Based on this event, it fetches ticket data, creates a request record in a MySQL database, and displays it on a dedicated web page. The request can then progress through various states, ending with a validation step that connects to a Microsoft Access database to verify asset data.

We also implemented email functionality that allows updates to be made directly from email links, including support for attached images.

## 👥 Project Context

- Developed during my professional internship.
- Built and deployed by a team of **2 developers** in just over **3 months**.
- Fully deployed and tested in a real business environment.

## 🔐 Authentication

The app uses **JWT (JSON Web Tokens)** to validate and manage user sessions securely.

## ✉️ Email Integration

- Requests trigger emails with tracking codes.
- Emails contain action links that allow request status updates without logging in.
- Image attachments are supported.

## 🔁 Technologies Used

- **Backend**: Node.js, Express
- **Database**: MySQL, Access (for validation)
- **Frontend**: HTML, CSS, JavaScript
- **Authentication**: JWT
- **Email Service**: Nodemailer with HTML content and attachments

## ⚙️ How to Run

> ⚠️ This project depends on a live osTicket instance and a configured database trigger.

1. Clone the repository.
2. Set up `.env` variables with your local credentials, osTicket database, and email settings.
3. Ensure the osTicket trigger is installed and running.
4. Run the application using:

```bash
npm install
node index.js
