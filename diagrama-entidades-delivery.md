# 🚀 Arquitetura da API de Delivery

## 📋 Visão Geral
Este documento descreve a arquitetura completa de uma API de delivery, incluindo entidades, serviços, controladores e padrões utilizados.

---

## 1️⃣ **Entidades e Herança (Models + Inheritance)**

### 📊 Diagrama de Classes
```mermaid
classDiagram
    title Sistema de Entidades - Herança e Relacionamentos
    
    %% Classe Base
    class User {
        <<abstract>>
        -Long id
        -String name
        -String email
        -String passwordHash
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +login() boolean
        +register() boolean
        +updateProfile() void
    }

    %% Entidades Específicas
    class Customer {
        -String phone
        -String address
        -String city
        -String state
        +placeOrder(Order) Order
        +viewOrders() List~Order~
        +updateAddress(String) void
    }

    class Restaurant {
        -String taxId
        -String name
        -String description
        -String address
        -String phone
        -Boolean isActive
        +manageMenu() void
        +receiveOrder(Order) void
        +updateStatus(OrderStatus) void
    }

    class Courier {
        -String vehicle
        -String licensePlate
        -String phone
        -CourierStatus status
        -Location currentLocation
        +acceptDelivery(Order) boolean
        +updateStatus(OrderStatus) void
        +updateLocation(Location) void
    }

    %% Entidade de Pedido
    class Order {
        -Long id
        -OrderStatus status
        -BigDecimal totalAmount
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        -LocalDateTime estimatedDelivery
        +updateStatus(OrderStatus) void
        +calculateTotal() BigDecimal
        +isDeliverable() boolean
    }

    %% Relacionamentos de Herança
    User <|-- Customer : extends
    User <|-- Restaurant : extends
    User <|-- Courier : extends

    %% Relacionamentos de Associação
    Customer "1" --> "0..*" Order : places
    Restaurant "1" --> "0..*" Order : receives
    Courier "0..1" --> "0..*" Order : delivers
```

### 🔍 **Explicação das Entidades:**
- **User**: Classe abstrata base com dados comuns (nome, email, senha)
- **Customer**: Cliente que faz pedidos
- **Restaurant**: Restaurante que recebe e processa pedidos
- **Courier**: Entregador que aceita e entrega pedidos
- **Order**: Pedido central que conecta todas as entidades

---

## 2️⃣ **Camada de Serviços e Controladores**

### 📊 Arquitetura de Controllers e Services
```mermaid
classDiagram
    %% Controllers (Camada de Apresentação)
    class CustomerController {
        +POST /customers/orders
        +GET /customers/:id/orders
        +PUT /customers/:id/profile
        +GET /customers/:id/profile
    }
    
    class RestaurantController {
        +GET /restaurants/:id/menu
        +POST /restaurants/:id/orders/:orderId/ack
        +GET /restaurants/:id/orders
        +PUT /restaurants/:id/menu
    }
    
    class CourierController {
        +POST /couriers/:id/orders/:orderId/accept
        +PUT /couriers/:id/orders/:orderId/status
        +GET /couriers/:id/orders
        +PUT /couriers/:id/location
    }
    
    class OrderController {
        +GET /orders/:id
        +PUT /orders/:id/status
        +GET /orders
        +POST /orders
    }

    %% Services (Camada de Negócio)
    class CustomerService {
        +placeOrder(Customer, OrderRequest) Order
        +listOrders(Long customerId) List~Order~
        +updateProfile(Long customerId, ProfileUpdateRequest) Customer
        +getProfile(Long customerId) Customer
    }
    
    class RestaurantService {
        +getMenu(Long restaurantId) List~MenuItem~
        +acknowledgeOrder(Long orderId) Order
        +listOrders(Long restaurantId) List~Order~
        +updateMenu(Long restaurantId, MenuUpdateRequest) void
    }
    
    class CourierService {
        +acceptDelivery(Long orderId, Long courierId) Order
        +updateOrderStatus(Long orderId, OrderStatus) Order
        +listAssignedOrders(Long courierId) List~Order~
        +updateLocation(Long courierId, Location) void
    }
    
    class OrderService {
        +getById(Long id) Order
        +updateStatus(Long id, OrderStatus) Order
        +listOrders(OrderFilter filter) List~Order~
        +createOrder(OrderRequest) Order
    }
    
    %% Classe "Order" central para apontar relações
    class Order

    %% Relacionamentos
    CustomerController --> CustomerService : uses
    RestaurantController --> RestaurantService : uses
    
    CourierController --> CourierService : uses
    OrderController --> OrderService : uses
    
    CustomerService --> Order : manages
    RestaurantService --> Order : processes
    
    CourierService --> Order : delivers
    OrderService --> Order : manages

    %% Forçar CourierController e CourierService abaixo
    CustomerService --> CustomerController : related
    RestaurantService --> RestaurantController : related

    %% Forçar CourierController e CourierService abaixo de Order
    Order --> CourierController : triggers
    Order --> CourierService : triggers

    %% Forçar OrderController e OrderService abaixo de Order
    Order --> OrderController : triggers
    Order --> OrderService : triggers

```

### 🔍 **Explicação da Arquitetura:**
- **Controllers**: Recebem requisições HTTP e delegam para Services
- **Services**: Contêm toda a lógica de negócio e validações
- **Separação de Responsabilidades**: Cada camada tem uma função específica

---

## 3️⃣ **DTOs e Mappers (Transferência de Dados)**

### 📊 Estrutura de DTOs e Mappers
```mermaid
classDiagram
    title DTOs e Mappers Padrao de Transferencia de Dados
    
    %% DTOs de Entrada (Requests)
    class CustomerCreateRequest {
        +String name
        +String email
        +String password
        +String phone
        +String address
    }
    
    class OrderCreateRequest {
        +Long restaurantId
        +List~OrderItemRequest~ items
        +String deliveryAddress
        +String notes
    }
    
    class OrderStatusUpdateRequest {
        +OrderStatus status
        +String reason
        +String notes
    }

```
```mermaid
classDiagram
    %% DTOs de Saída (Responses)
    class CustomerDTO {
        +Long id
        +String name
        +String email
        +String phone
        +String address
        +LocalDateTime createdAt
    }
    
    class RestaurantDTO {
        +Long id
        +String name
        +String description
        +String address
        +String phone
        +Boolean isActive
        +List~MenuItemDTO~ menu
    }
    
    %% Mappers
    class CustomerMapper {
        +toDTO(Customer) CustomerDTO
        +toEntity(CustomerCreateRequest) Customer
        +updateEntity(Customer, CustomerUpdateRequest) Customer
    }
    
    class RestaurantMapper {
        +toDTO(Restaurant) RestaurantDTO
        +toEntity(RestaurantCreateRequest) Restaurant
        +updateEntity(Restaurant, RestaurantUpdateRequest) Restaurant
    }
    

    %% Relacionamentos
    CustomerMapper --> Customer : converts
    CustomerMapper --> CustomerDTO : converts
    RestaurantMapper --> Restaurant : converts
    RestaurantMapper --> RestaurantDTO : converts
```

```mermaid
classDiagram
    %% DTOs de Saída (Responses)
    class CourierDTO {
        +Long id
        +String name
        +String vehicle
        +String licensePlate
        +CourierStatus status
        +Location currentLocation
    }
    
    class OrderDTO {
        +Long id
        +OrderStatus status
        +BigDecimal totalAmount
        +LocalDateTime createdAt
        +LocalDateTime estimatedDelivery
        +CustomerDTO customer
        +RestaurantDTO restaurant
        +CourierDTO courier
        +List~OrderItemDTO~ items
    }

    %% Mappers
    class CourierMapper {
        +toDTO(Courier) CourierDTO
        +toEntity(CourierCreateRequest) Courier
        +updateEntity(Courier, CourierUpdateRequest) Courier
    }
    
    class OrderMapper {
        +toDTO(Order) OrderDTO
        +toEntity(OrderCreateRequest) Order
        +updateEntity(Order, OrderStatusUpdateRequest) Order
    }
    %% Relacionamentos
    CourierMapper --> Courier : converts
    CourierMapper --> CourierDTO : converts
    OrderMapper --> Order : converts
    OrderMapper --> OrderDTO : converts

````

### 🔍 **Explicação dos DTOs:**
- **Request DTOs**: Dados de entrada para criação/atualização
- **Response DTOs**: Dados de saída para o cliente
- **Mappers**: Convertem entre entidades e DTOs, mantendo separação de camadas

---

## 4️⃣ **Camada de Apresentação (Views)**

### 📊 Estrutura de Views
```mermaid
classDiagram
    title Camada de Apresentacao - Views
    
    %% Views para diferentes tipos de usuário
    class CustomerView {
        +showProfile(CustomerDTO) String
        +showOrders(List~OrderDTO~) String
        +showOrderDetails(OrderDTO) String
        +showRestaurantMenu(List~MenuItemDTO~) String
    }
    
    class RestaurantView {
        +showMenu(List~MenuItemDTO~) String
        +showIncomingOrders(List~OrderDTO~) String
        +showOrderDetails(OrderDTO) String
        +showAnalytics(RestaurantAnalytics) String
    }

    %% Relacionamentos com Controllers
    CustomerController ..> CustomerView : uses
    RestaurantController ..> RestaurantView : uses

```
```mermaid
classDiagram
    class CourierView {
        +showAssignedOrders(List~OrderDTO~) String
        +showOrderDetails(OrderDTO) String
        +showDeliveryRoute(List~OrderDTO~) String
        +showEarnings(CourierEarnings) String
    }
    
    class OrderView {
        +showOrderDetails(OrderDTO) String
        +showOrderStatus(OrderDTO) String
        +showOrderHistory(List~OrderDTO~) String
        +showTrackingInfo(OrderDTO) String
    }

    %% Relacionamentos com Controllers
    CourierController ..> CourierView : uses
    OrderController ..> OrderView : uses
```

### 🔍 **Explicação das Views:**
- **CustomerView**: Interface para clientes visualizarem pedidos e perfil
- **RestaurantView**: Interface para restaurantes gerenciarem pedidos e menu
- **CourierView**: Interface para entregadores acompanharem entregas
- **OrderView**: Interface genérica para visualização de pedidos

---

## 5️⃣ **Camada de Persistência (Repositories)**

### 📊 Estrutura de Repositories
```mermaid
classDiagram
    title Camada de Persistência - Repositories
    
    %% Repositories para cada entidade
    class CustomerRepository {
        +findById(Long id) Optional~Customer~
        +findByEmail(String email) Optional~Customer~
        +findAll() List~Customer~
        +save(Customer customer) Customer
        +deleteById(Long id) void
        +existsByEmail(String email) boolean
    }
    
    class RestaurantRepository {
        +findById(Long id) Optional~Restaurant~
        +findByTaxId(String taxId) Optional~Restaurant~
        +findByCity(String city) List~Restaurant~
        +findActiveRestaurants() List~Restaurant~
        +save(Restaurant restaurant) Restaurant
        +deleteById(Long id) void
    }
    


    %% Relacionamentos com entidades
    CustomerRepository --> Customer : manages
    RestaurantRepository --> Restaurant : manages
```
```mermaid
classDiagram
    title Camada de Persistência - Repositories
    
    %% Repositories para cada entidade
    class CourierRepository {
        +findById(Long id) Optional~Courier~
        +findByVehicle(String vehicle) List~Courier~
        +findAvailableCouriers() List~Courier~
        +findByStatus(CourierStatus status) List~Courier~
        +save(Courier courier) Courier
        +deleteById(Long id) void
    }
    
    class OrderRepository {
        +findById(Long id) Optional~Order~
        +findByCustomerId(Long customerId) List~Order~
        +findByRestaurantId(Long restaurantId) List~Order~
        +findByCourierId(Long courierId) List~Order~
        +findByStatus(OrderStatus status) List~Order~
        +save(Order order) Order
        +deleteById(Long id) void
    }

    CourierRepository --> Courier : manages
    OrderRepository --> Order : manages
```

### 🔍 **Explicação dos Repositories:**
- **CustomerRepository**: Gerencia persistência de clientes
- **RestaurantRepository**: Gerencia persistência de restaurantes
- **CourierRepository**: Gerencia persistência de entregadores
- **OrderRepository**: Gerencia persistência de pedidos

---

## 6️⃣ **Fluxo de Dados Completo**

### 📊 Fluxo de uma Requisição
```mermaid
sequenceDiagram
    title Fluxo Completo de uma Requisição de Pedido
    
    participant Client as Cliente
    participant Controller as CustomerController
    participant Service as CustomerService
    participant Repository as CustomerRepository
    participant OrderService as OrderService
    participant OrderRepo as OrderRepository
    participant DB as Database
    
    Client->>Controller: POST /customers/{id}/orders
    Controller->>Service: placeOrder(customerId, orderRequest)
    Service->>Repository: findById(customerId)
    Repository->>DB: SELECT * FROM customers WHERE id = ?
    DB-->>Repository: Customer data
    Repository-->>Service: Customer entity
    Service->>OrderService: createOrder(orderRequest)
    OrderService->>OrderRepo: save(order)
    OrderRepo->>DB: INSERT INTO orders (...)
    DB-->>OrderRepo: Order with ID
    OrderRepo-->>OrderService: Order entity
    OrderService-->>Service: Order entity
    Service-->>Controller: Order entity
    Controller-->>Client: OrderDTO (201 Created)
```

---

## 7️⃣ **Padrões e Boas Práticas Utilizados**

### ✅ **Padrões de Projeto:**
- **MVC (Model-View-Controller)**: Separação clara de responsabilidades
- **Repository Pattern**: Abstração da camada de dados
- **Service Layer Pattern**: Lógica de negócio centralizada
- **DTO Pattern**: Transferência segura de dados entre camadas
- **Mapper Pattern**: Conversão entre entidades e DTOs

### ✅ **Princípios SOLID:**
- **Single Responsibility**: Cada classe tem uma responsabilidade
- **Open/Closed**: Extensível sem modificação
- **Liskov Substitution**: Herança bem implementada
- **Interface Segregation**: Interfaces específicas para cada necessidade
- **Dependency Inversion**: Dependências através de abstrações

### ✅ **Estrutura de Pacotes Recomendada:**
```
com.deliverytech.delivery/
├── controller/          # Controladores REST
├── service/            # Lógica de negócio
├── repository/         # Acesso a dados
├── model/             # Entidades JPA
├── dto/               # Objetos de transferência
├── mapper/            # Conversores
├── config/            # Configurações
├── exception/         # Exceções customizadas
└── util/              # Utilitários
```

---

## 🎯 **Próximos Passos para Implementação:**

1. **Configurar projeto Spring Boot** com dependências necessárias
2. **Implementar entidades JPA** com anotações apropriadas
3. **Criar repositories** com Spring Data JPA
4. **Implementar services** com lógica de negócio
5. **Criar controllers REST** com endpoints documentados
6. **Implementar DTOs e mappers** para transferência de dados
7. **Adicionar validações** e tratamento de exceções
8. **Configurar banco de dados** e migrations
9. **Implementar testes unitários** e de integração
10. **Documentar API** com Swagger/OpenAPI

---

*📝 Este diagrama serve como base para implementação de uma API de delivery robusta e escalável.*