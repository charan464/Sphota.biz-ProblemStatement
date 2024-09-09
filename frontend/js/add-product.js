document.addEventListener('DOMContentLoaded', () => {
    fetchCategories();
});

async function fetchCategories() {
    const token = localStorage.getItem('jwtToken');
    const response = await fetch('http://localhost:8085/admin/categories', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    const categories = await response.json();
    const categorySelect = document.getElementById('category');
    categorySelect.innerHTML = '<option value="" disabled selected>Select a category</option>';

    // Iterate over each category and create an option element for the dropdown

    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.textContent = category.name;
        categorySelect.appendChild(option);
    });
}



document.getElementById('addProductForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const price = document.getElementById('price').value;
    const categoryId = document.getElementById('category').value;
    const token = localStorage.getItem('jwtToken');

    const product = {
        name: name,
        price: price,
        category: {
            id: categoryId
        }
    };

    const response = await fetch('http://localhost:8085/admin/addProduct', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(product),
    });

    const result = await response.json();

    if (response.ok) {
        toastr.success(result.message);
    } else {
        toastr.error(result.message || 'Unable to add product');
    }
});