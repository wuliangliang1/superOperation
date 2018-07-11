<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>OCR</title>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript">
        function doUpload() {
            document.getElementById("upload-button").style.visibility="hidden";
            var formData = new FormData();
            formData.append("file1", $('#file1')[0].files[0]);
            $.ajax({
                url: '/ll/bd/ocr/doUpload' ,
                type: 'post',
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                async: false
            }).done(function(res) {
                $("#text").html(res);
                document.getElementById("upload-button").style.visibility="visible";
            }).fail(function(res) {
                alert(res);
            });
        }
    </script>
</head>
<body>
<form id= "uploadForm">
    <p >上传文件： <input type="file" name="file1" id="file1"  multiple="multiple"  /></p>
    <input id="upload-button" type="button" value="上传" onclick="doUpload()" />
</form>
<div id="text" style="width:auto;height:auto;margin: auto;border: 1px solid black"></div>
</body>
</html>