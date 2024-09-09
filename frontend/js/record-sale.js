document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    document.getElementById('recordSale')?.addEventListener('click', recordSaleAndRedirect);
    document.getElementById('addProductButton')?.addEventListener('click', addProductToList);
});

let saleItems = [];
let productData = [];

async function fetchProducts() {
    const token = localStorage.getItem('jwtToken');

    const response = await fetch('http://localhost:8085/user/products', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    productData = await response.json();
    const productSelect = document.getElementById('productSelect');

    // Iterate over each product and create an option element for the dropdown

    productData.forEach(product => {
        const option = document.createElement('option');
        option.value = product.id;
        option.textContent = product.name;
        productSelect.appendChild(option);
    });
}

function addProductToList() {
    const productId = document.getElementById('productSelect').value;
    const quantity = document.getElementById('quantity').value;

    if (!productId || !quantity || quantity <= 0) {
        alert('Please select a product and enter a valid quantity.');
        return;
    }

    const selectedProduct = productData.find(product => product.id == productId);
    if (!selectedProduct) {
        alert('Selected product not found.');
        return;
    }

    const gstAmount = (selectedProduct.price * selectedProduct.category.gstRate) / 100;
    const total = selectedProduct.price + gstAmount;

    //creating a sale item and adding it to the sale items list
    // a sale item is a combination of product,quantity,gstAmount and total amount

    const saleItem = {
        product: {
            id: selectedProduct.id,
            name: selectedProduct.name,
            price: selectedProduct.price,
            gstRate: gstAmount,
            categoryName: selectedProduct.category.name
        },
        quantity: parseInt(quantity),
        gstAmount: gstAmount,
        total: total * quantity
    };

    saleItems.push(saleItem);
    displaySaleItems();
    clearForm();
}

function displaySaleItems() {
    const saleItemsTableBody = document.querySelector('#saleItemsTable tbody');
    saleItemsTableBody.innerHTML = '';

    // add details of each sale item as a row to the table
    // adding delete button to allow user to remove added sale item

    saleItems.forEach((item, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.product.name}</td>
            <td>${item.product.categoryName}</td>
            <td>${item.product.price.toFixed(2)}</td>
            <td>${item.gstAmount.toFixed(2)}</td>
            <td>${item.quantity}</td>
            <td>${item.total.toFixed(2)}</td>
            <td><button type="button" class="deleteButton" data-index="${index}">Delete</button></td>
        `;
        saleItemsTableBody.appendChild(row);
    });

    document.querySelectorAll('.deleteButton').forEach(button => {
        button.addEventListener('click', function () {
            const index = parseInt(this.getAttribute('data-index'));
            saleItems.splice(index, 1);
            displaySaleItems();
        });
    });
}

function clearForm() {
    document.getElementById('productSelect').value = '';
    document.getElementById('quantity').value = '';
}

async function recordSaleAndRedirect() {
    if (saleItems.length === 0) {
        alert('Please add at least one product to the sale.');
        return;
    }

    const salePayload = {
        items: saleItems.map(item => ({
            product: { id: item.product.id },
            quantity: item.quantity
        }))
    };


    const token = localStorage.getItem('jwtToken');
    const response = await fetch('http://localhost:8085/user/recordSaleAndGenerateBill', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(salePayload)
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem('saleItems', JSON.stringify(saleItems));
        localStorage.setItem('saleResponse', JSON.stringify(data));
        window.location.href = 'view-bill.html';
    } else {
        toastr.error('unable to record sale');
    }
}

