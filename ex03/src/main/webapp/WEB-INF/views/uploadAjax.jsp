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
	justify-content: center;
	align-items: center;
}

.uploadResult ul li img{
	width: 80px;
}

.bigPictureWrapper{
	position: relative;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	background-color: gray;
	z-index: 100;
	/* background:rgba(255,255,255,0.5); */
}

.bigPicture{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img{
	width: 600px;
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
	
	<div class="bigPictureWrapper">
		<div class="bigPicture">
		
		</div>
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
			//alert(fileCallPath);
			
			$(".bigPictureWrapper").css("display", "flex").show();
			
			$(".bigPicture").html("<img src='/display?fileName="+encodeURI(fileCallPath)+"'>").animate({width:'100%', height:'100%'}, 1000);
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
						str += "<li><div><a href='/download?fileName="+fileCallPath+"'><img src='/resources/img/attach.png'>"+obj.fileName+"</a>"+
								"<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+
								"</div></li>";
						
					} else{
						
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
							
						var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
						originPath = originPath.replace(new RegExp(/\\/g), "/");
						
						str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a>"+
							   "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+
							   "</li>";
					}
					
				});
				uploadResult.append(str);
			}
			
			$(".bigPictureWrapper").on("click", function(e){
				$(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
				setTimeout(() => {
					$(this).hide();
				}, 1000);
			})
			
			$(".uploadResult").on("click","span", function(e){
				var targetFile = $(this).data("file");
				var type = $(this).data("type");
				console.log(targetFile);
				
				$.ajax({
					url: '/deleteFile',
					data: {fileName: targetFile, type:type},
					dataType: 'text',
					type: 'POST',
					success: function(result){
						alert(result);
					}
				})
			})
			
		});
	</script>
  
  
  
</body>
</html>