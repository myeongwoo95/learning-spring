<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.uploadResult{
	width: 100%;
	background-color: gray;
}

.uploadResult ul{
	display: flex;
	felx-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li{
	list-style: none;
	padding: 10px;
}

.uploadResult ul li img{
	width: 80px;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Upload with Ajax</h1>
	
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	
	<button id="uploadBtn">Upload</button>
	
	<div class="uploadResult">
		<ul>
			
		</ul>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	  		integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	  		crossorigin="anonymous">
	</script>
	
	<script>
		/* 
		$(document).ready() 바깥쪽에 작성하는 이유는 나중에
		<a> 태그에 직접 showImage()를 호출할 수 있는 방식으로 작성하기 위해 
		ready() 안에 작성하는 경우 함수 호출 시 정의되지 않은 함수라고 에러 출력가 출력됨
		*/
		function showImage(fileCallPath){
			alert(fileCallPath);
		}
		
		$(document).ready(function(){
			
			var cloneObj = $(".uploadDiv").clone();
			var uploadResult = $(".uploadResult ul");
			
			var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
			var maxSize = 5242880; // 5MB
			
			function checkExtension(fileName, fileSize){
				
				if(fileSize >= maxSize){
					alert("파일 사이즈 초과");
					return false;
				}
				
				if(regex.test(fileName)){
					alert("해당 종류의 파일은 업로드 할 수 없습니다.");
					return false;
				}
				return true;
			}
			
			$("#uploadBtn").on("click", function(e){
				
				var formData = new FormData();
				
				var inputFile = $("input[name='uploadFile']");
				
				var files = inputFile[0].files;
				
				console.log(files);
				
				//add file Data to formData
				for(var i = 0 ; i < files.length ; i++){
					
					if(!checkExtension(files[i].name, files[i].size)){
						return false;
					}
					
					formData.append("uploadFile", files[i]);
				}
				
				$.ajax({
					url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData,
					type: 'POST',
					dataType: 'json',
					success: function(result){
						console.log(result)
						
						showUploadedFile(result);
						
						$(".uploadDiv").html(cloneObj.html());
						
					}  
				});
			})
			
			function showUploadedFile(uploadResultArr){
				
				var str = "";
				
				$(uploadResultArr).each(function(index, obj){
					
					if(!obj.image){
						
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						str += "<li><a href='/download?fileName="+fileCallPath+"'><img src='/resources/img/attach.png'>"+obj.fileName+"</a></li>";
						
					} else{
						
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
							
						var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
						originPath = originPath.replace(new RegExp(/\\/g), "/");
						
						str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a></li>";
					}
					
				});
				uploadResult.append(str);
			}
		});
	</script>
  
  
  
</body>
</html>