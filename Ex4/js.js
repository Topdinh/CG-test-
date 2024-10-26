const customers = [];

document.getElementById("customerForm").addEventListener("submit", function(event) {
    event.preventDefault(); 

    const customerId = document.getElementById("customerId").value;
    const customerName = document.getElementById("customerName").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;
    const email = document.getElementById("email").value;

    const newCustomer = {
        id: customerId,
        name: customerName,
        phone: phone,
        address: address,
        email: email
    };

    customers.push(newCustomer);

    displayCustomers();

    document.getElementById("customerForm").reset();
});

function displayCustomers() {
    const customerTableBody = document.getElementById("customerTable").getElementsByTagName("tbody")[0];
    customerTableBody.innerHTML = ""; 

    customers.forEach(customer => {
        const row = customerTableBody.insertRow();

        const cellId = row.insertCell(0);
        const cellName = row.insertCell(1);
        const cellPhone = row.insertCell(2);
        const cellAddress = row.insertCell(3);
        const cellEmail = row.insertCell(4);

        cellId.textContent = customer.id;
        cellName.textContent = customer.name;
        cellPhone.textContent = customer.phone;
        cellAddress.textContent = customer.address;
        cellEmail.textContent = customer.email;
    });
}