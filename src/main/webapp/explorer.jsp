<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        td {
            padding-right: 10px;
        }
        table {
            border: solid 0.5vw;
            animation: rainbow 1s linear infinite;
        }

        @keyframes rainbow {
            0% { border-color: red }
            20% { border-color: orange }
            40% { border-color: yellow }
            60% { border-color: limegreen }
            80% { border-color: blue }
            100% { border-color: purple }
        }
        img {
            position: relative;
            top: 3px;
        }
    </style>
    <title>Directories</title>
</head>
<body>
<style>
    body {
        background: url(https://images.hdqwalls.com/download/ios-11-heritage-stripe-red-yb-1920x1080.jpg) no-repeat;
        background-size: 100%;
    }
</style>
<div style="margin-left: 10px">${now}</div>
<h1 style="margin-left: 10px">${name}</h1>

<div class="content" style="margin-left: 450px">
    <table>
        <tr>
            <th>Файл</th>
            <th>Размер</th>
            <th>Дата</th>
        </tr>
        ${folders}
        ${files}
    </table>
</div>
</body>
</html>