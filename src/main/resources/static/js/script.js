const baseURL = 'http://localhost:8080';


function fetchProducts() {
  fetch(`${baseURL}/products`)
    .then(response => response.json())
    .then(products => {
      const productTableBody = document.getElementById('productTableBody');
      productTableBody.innerHTML = '';

      products.forEach(product => {
        const row = `
          <tr>
            <td>${product.product_id}</td>
            <td>${product.product_name}</td>
            <td>${product.price}</td>
            <td>${product.quantity}</td>
            <td>${product.description}</td>
            <td>${product.category.category_Name}</td>
          </tr>
        `;
        productTableBody.innerHTML += row;
      });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}


function fetchCategories() {
  fetch(`${baseURL}/categories`)
    .then(response => response.json())
    .then(categories => {
      const productCategorySelect = document.getElementById('productCategory');
      const categoryTableBody = document.getElementById('categoryTableBody');
      productCategorySelect.innerHTML = '';
      categoryTableBody.innerHTML = '';
      categories.forEach(category => {
        const option = `<option value="${category.category_id}">${category.category_Name}</option>`;
        productCategorySelect.innerHTML += option;

        const row = `
          <tr>
            <td>${category.category_id}</td>
            <td>${category.category_Name}</td>
            <td>${category.c_description}</td>
            
          </tr>
        `;
        categoryTableBody.innerHTML += row;
      });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}


function addProduct() {
  const productName = document.getElementById('product_name').value;
  const price = document.getElementById('price').value;
  const quantity = document.getElementById('quantity').value;
  const description = document.getElementById('description').value;
  const category_id = document.getElementById('productCategory').value;

  fetch(`${baseURL}/categories/${category_id}`)
    .then(response => response.json())
    .then(category => {
    
      const category_Name = category.category_Name;
      const c_description = category.c_description;

     
      const product = {
        product_name: productName,
        price: parseFloat(price),
        quantity: parseInt(quantity),
        description: description,
        category: {
          category_id: category_id,
          category_Name: category_Name,
          c_description: c_description
        }
      };

      
      fetch(`${baseURL}/products/addProduct`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
      })
        .then(response => response.json())
        .then(data => {
          console.log('Product added successfully:', data);
          fetchProducts();
          // Clear the input fields
          document.getElementById('product_name').value = '';
          document.getElementById('price').value = '';
          document.getElementById('quantity').value = '';
          document.getElementById('description').value = '';
          document.getElementById('productCategory').selectedIndex = 0;
        })
        .catch(error => {
          console.error('Error:', error);
        });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}



function addCategory() {
  const category_Name = document.getElementById('category_Name').value;
  const c_description = document.getElementById('c_description').value;

  const category = {
    category_Name,
    c_description
  };

  fetch(`${baseURL}/categories/addCategory`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(category)
  })
    .then(response => response.json())
    .then(data => {
      console.log('Category added successfully:', data);
      fetchCategories();
  
      document.getElementById('category_Name').value = '';
      document.getElementById('c_description').value = '';
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function updatePrice() {
  const productId = document.getElementById('product_id').value;
  const newPrice = document.getElementById('new_price').value;

  // Format the data as url-encoded form data
  const formData = new URLSearchParams();
  formData.append('price', newPrice);

  // Make an api call to update the price
  fetch(`${baseURL}/products/${productId}/price`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: formData.toString() // Convert the data to a string
  })
    .then(response => {
      if (response.ok) {
        console.log('Price updated successfully');
        fetchProducts();
        fetchCategories();
      } else {
        console.error('Failed to update price:', response.statusText);
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}


function updateQuantity() {
  const productId = document.getElementById('product_id').value;
  const newQuantity = document.getElementById('new_quantity').value;

  const formData = new URLSearchParams();
  formData.append('quantity', newQuantity);


  fetch(`${baseURL}/products/${productId}/quantity`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: formData.toString() // Convert the data to a string
  })
    .then(response => {
      if (response.ok) {
        console.log('Quantity updated successfully');
        fetchProducts();
        fetchCategories();
      } else {
        console.error('Failed to update quantity:', response.statusText);
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}
function updateCategory() {
  const categoryId = document.getElementById('category_id').value;
  const categoryName = document.getElementById('new_category_Name').value;
  const categoryDescription = document.getElementById('new_c_description').value;

  const updatedCategory = {
    category_Name: categoryName,
    c_description: categoryDescription
  };

  fetch(`${baseURL}/categories/${categoryId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(updatedCategory)
  })
    .then(response => {
      if (response.ok) {
        console.log('Category updated successfully');
        fetchCategories(); // Refresh the category list
        document.getElementById('updateCategoryForm').reset();
      } else {
        console.error('Failed to update category:', response.statusText);
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

fetchProducts();
fetchCategories();
