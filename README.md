# Online-market
Online market (copy of Yandex market). RESTful application on java Spring.

<h3>URLs</h3>

<ol>
  <li><a href="#1">/api/v1/imports</a></li>
  <li><a href="#2">/api/v1/delete/{id}</a></li>
  <li><a href="#3">/api/v1/nodes/{id}</a></li>
  <li><a href="#4">/api/v1/sales?date=...</a></li>
</ol>

<h2 id="1">Imports</h2>
Adding or updating offer/category.

Example:

Request:
```json
{
    "items": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66a129",
      "name": "offer6",
      "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
      "price": 5,
      "type": "OFFER"
    }
  ],
  "updateDate": "2022-05-29T21:12:01.000Z"
}
```

Response:

```json
{
    "code": 200,
    "message": "Operation completed successfully"
}
```

Request:

```json
{
    "items": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66a129",
      "name": "offer6",
      "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
      "price": null,
      "type": "OFFER"
    }
  ],
  "updateDate": "2022-05-29T21:12:01.000Z"
}
```

Response 

```json
{
    "code": 400,
    "message": "Validation Failed"
}
```


<h2 id="2">Delete</h2>

Deleting offer/category

Request:

localhost:8080/api/v1/delete/3fa85f64-5717-4562-b3fc-2c963f66a130
Body is empty

Response:

```json
{
    "code": 200,
    "message": "Operation completed successfully"
}
```

Request:

localhost:8080/api/v1/delete/3fa85f64-5717-4562-b3fc-2c963f66a132
Body is empty

Response:

```json
{
    "code": 404,
    "message": "Item not found"
}
```

<h2 id="3">Nodes</h2>

Get offer/product and its children

Request:

localhost:8080/api/v1/nodes/3fa85f64-5717-4562-b3fc-2c963f66a111
Body is empty

Responde:

```json
{
    "id": "3fa85f64-5717-4562-b3fc-2c963f66a111",
    "name": "offer1",
    "type": "CATEGORY",
    "price": 222226,
    "updateDate": "2022-05-28T18:00:00.000Z",
    "parentId": null,
    "children": [
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a112",
            "name": "offer1",
            "type": "OFFER",
            "price": 1000000,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a113",
            "name": "offer1",
            "type": "OFFER",
            "price": 1000000,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a128",
            "name": "offer53",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-29T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a114",
            "name": "offer1",
            "type": "OFFER",
            "price": 1000000,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a115",
            "name": "offer1",
            "type": "OFFER",
            "price": 1000000,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a116",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a129",
            "name": "offer2",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-29T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a117",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a118",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a119",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a120",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a121",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a122",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a123",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a124",
            "name": "offer1",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-28T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a126",
            "name": "offer50",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-29T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        },
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66a125",
            "name": "offer9",
            "type": "OFFER",
            "price": 5,
            "updateDate": "2022-05-29T18:00:00.000Z",
            "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
            "children": []
        }
    ]
}
```

Request:

localhost:8080/api/v1/nodes/3fa85f64-5717-4562-b3fc-2c963f66a190
Body is empty

Response:

```json
{
    "code": 404,
    "message": "Item not found"
}
```

<h2 id="4">Sales</h2>

Return offers/products that was updated last day or earlier

Request:

localhost:8080/api/v1/sales?date=2022-05-30T11:00:00.000Z
Body is empty

Response:

```json
[
    {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66a128",
        "name": "offer53",
        "type": "OFFER",
        "price": 5,
        "updateDate": "2022-05-29T18:00:00.000Z",
        "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
        "children": []
    },
    {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66a129",
        "name": "offer2",
        "type": "OFFER",
        "price": 5,
        "updateDate": "2022-05-29T18:00:00.000Z",
        "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
        "children": []
    },
    {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66a126",
        "name": "offer50",
        "type": "OFFER",
        "price": 5,
        "updateDate": "2022-05-29T18:00:00.000Z",
        "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
        "children": []
    },
    {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66a125",
        "name": "offer9",
        "type": "OFFER",
        "price": 5,
        "updateDate": "2022-05-29T18:00:00.000Z",
        "parentId": "3fa85f64-5717-4562-b3fc-2c963f66a111",
        "children": []
    }
]
```

Request:

localhost:8080/api/v1/sales?date=2022-05-30T11:00:00.000
Body is empty

Response:

```json
{
    "code": 400,
    "message": "Validation Failed"
}
```
