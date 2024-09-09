document.addEventListener('DOMContentLoaded', () => {
    const saleItems = JSON.parse(localStorage.getItem('saleItems'));
    if (!saleItems || saleItems.length === 0) {
        alert('No sale items found.');
        return;
    }

    toastr.success('Sale recorded');

    const billTableBody = document.querySelector('#billTable tbody');
    let totalPrice = 0;

    //add details of each sale item as a row to the table

    saleItems.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.product.name}</td>
            <td>${item.product.categoryName}</td>
            <td>${item.product.price.toFixed(2)}</td>
            <td>${item.gstAmount.toFixed(2)}</td>
            <td>${item.quantity}</td>
            <td>${item.total.toFixed(2)}</td>
        `;
        billTableBody.appendChild(row);
        totalPrice += item.product.price * item.quantity;
    });

    const saleResponse = JSON.parse(localStorage.getItem('saleResponse'));

    document.getElementById('amount').textContent = `Total Amount (Without Tax) : ${totalPrice.toFixed(2)}`;
    document.getElementById('totalGST').textContent = `Total Tax : ${saleResponse.taxAmount.toFixed(2)}`;
    document.getElementById('totalAmount').textContent = `Total Amount (With Tax) : ${saleResponse.totalAmount.toFixed(2)}`;
});
