# Test API Script
$baseUrl = "http://localhost:8080/api"
$userId = "user123"

function Test-Endpoint {
    param($method, $uri, $body)
    Write-Host "Testing $method $uri" -ForegroundColor Cyan
    try {
        if ($body) {
            $response = Invoke-RestMethod -Method $method -Uri "$baseUrl$uri" -Body ($body | ConvertTo-Json) -ContentType "application/json"
        } else {
            $response = Invoke-RestMethod -Method $method -Uri "$baseUrl$uri"
        }
        Write-Host "Success:`n" ($response | ConvertTo-Json -Depth 5) -ForegroundColor Green
        return $response
    } catch {
        Write-Host "Failed: $_" -ForegroundColor Red
        if ($_.Exception.Response) {
             $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
             Write-Host $reader.ReadToEnd() -ForegroundColor Red
        }
    }
}

# 1. Create Product
$product = @{
    name = "Test Product"
    price = 100.0
    stock = 50
    description = "A sample product"
}
$createdProduct = Test-Endpoint "POST" "/products" $product

# 2. Add to Cart
if ($createdProduct.id) {
    $cartItem = @{
        userId = $userId
        productId = $createdProduct.id
        quantity = 2
    }
    Test-Endpoint "POST" "/cart/add" $cartItem
}

# 3. Get Cart
Test-Endpoint "GET" "/cart/$userId" $null

# 4. Create Order
$orderReq = @{
    userId = $userId
}
$order = Test-Endpoint "POST" "/orders" $orderReq

# 5. Process Payment
if ($order.id) {
    Test-Endpoint "POST" "/mock-payment/pay/$($order.id)?result=SUCCESS" $null
    
    # 6. Check Order Status
    Start-Sleep -Seconds 2
    Test-Endpoint "GET" "/orders/$($order.id)" $null
}

Write-Host "`nTest Sequence Completed" -ForegroundColor Yellow
