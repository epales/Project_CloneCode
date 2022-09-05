/**
 * 
 */
var fileArr;
var fileInfoArr = [];
var filesArr = new Array();
var b=1;
//썸네일 미리보기.
function previewImage(targetObj, View_area) {
	//var files = targetObj.files;
	//fileArr = Array.prototype.slice.call(files);

	var preview = document.getElementById(View_area); //div id
	var ua = window.navigator.userAgent;
	
	var maxFileCnt = 5;   // 첨부파일 최대 개수
    var attFileCnt = document.querySelectorAll('.addImg').length;    // 기존 추가된 첨부파일 개수
    var remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
    var curFileCnt = targetObj.files.length;  // 현재 선택된 첨부파일 개수

		
    // 첨부파일 개수 확인
    if (curFileCnt > remainFileCnt) {
		
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    }

    for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {

        const file = targetObj.files[i];
        //const file = files[i];
        // 첨부파일 검증
        if (validation(file)) {
            // 파일 배열에 담기
            var reader = new FileReader();
            reader.onload = function () {
                filesArr.push(file);
            };
            reader.readAsDataURL(file)
		//ie일때(IE8 이하에서만 작동)
			if (ua.indexOf("MSIE") > -1) {
				targetObj.select();
				try {
					var src = document.selection.createRange().text; // get file full path(IE9, IE10에서 사용 불가)
					var ie_preview_error = document.getElementById("ie_preview_error_" + View_area);
		
		
					if (ie_preview_error) {
						preview.removeChild(ie_preview_error); //error가 있으면 delete
					}
		
					var img = document.getElementById(View_area); //이미지가 뿌려질 곳
		
					//이미지 로딩, sizingMethod는 div에 맞춰서 사이즈를 자동조절 하는 역할
					img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + src + "', sizingMethod='scale')";
				} catch (e) {
					if (!document.getElementById("ie_preview_error_" + View_area)) {
						var info = document.createElement("<p>");
						info.id = "ie_preview_error_" + View_area;
						info.innerHTML = e.name;
						preview.insertBefore(info, null);
					}
				}
				//ie가 아닐때(크롬, 사파리, FF)
			} else {
		
					var imageType = /image.*/; //이미지 파일일경우만.. 뿌려준다.
					if (!file.type.match(imageType))
						continue;
					// var prevImg = document.getElementById("prev_" + View_area); //이전에 미리보기가 있다면 삭제
					// if (prevImg) {
					//     preview.removeChild(prevImg);
					// }

					var li = document.createElement('li');
					li.id = "img_id_" + b;
					li.className = "img_class";
					li.style.width = '202px';
					li.style.height = '202px';
					
					preview.appendChild(li);
		
					var img = document.createElement("img");
					img.className = "addImg";
					img.classList.add("obj");
					img.file = file;
					img.style.width = 'inherit';
					img.style.height = 'inherit';
					img.style.cursor = 'pointer';
					var p = document.createElement("b");
					p.textContent
					const idx = b;
					img.onclick = () => fileRemove(idx);   //이미지를 클릭했을 때.
					li.appendChild(img);
					b++;
					if (window.FileReader) { // FireFox, Chrome, Opera 확인.
						var reader = new FileReader();
						reader.onloadend = (function(aImg) {
							return function(e) {
								aImg.src = e.target.result;
							};
						})(img);
						reader.readAsDataURL(file);
					} else { // safari is not supported FileReader
						//alert('not supported FileReader');
						if (!document.getElementById("sfr_preview_error_"
							+ View_area)) {
							var info = document.createElement("p");
							info.id = "sfr_preview_error_" + View_area;
							info.innerHTML = "not supported FileReader";
							preview.insertBefore(info, null);
						}
					}
				}
			}
			else {
            continue;
        }
    }
   $("#input_file").val("");
}

//썸네일 클릭시 삭제.
function fileRemove(index) {
	console.log("index: " + index);
	var a = index;

	if(filesArr[4] !=null && index==4){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	if(filesArr[4]!=null && index==3){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	if(filesArr[4]!=null && index==2){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	if(filesArr[4]!=null && index==1){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	
	if(filesArr[3]!=null && index==3){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	if(filesArr[3]!=null && index==2){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	if(filesArr[3]!=null && index==1){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	
	if(filesArr[2]!=null && index==2){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	if(filesArr[2]!=null && index==1){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	
	if(filesArr[1]!=null && index==1){
		alert("끝에서 부터 이미지를 제거해주세요.");
		return false;
	}
	
	filesArr.splice((index-1), 1);
	
	var imgId = "#img_id_" + a;
	$(imgId).remove();
	
  
	console.log(filesArr);
	b--;
}

/* 첨부파일 검증 */
function validation(obj){
    const fileTypes = ['application/pdf', 'image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif', 'application/haansofthwp', 'application/x-hwp'];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > (100 * 1024 * 1024)) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf('.') == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else if (!fileTypes.includes(obj.type)) {
        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}

function findAddress(){
	window.onload = function(){
		       document.getElementById("address").addEventListener("click", function(){ //주소입력칸을 클릭하면
		        //카카오 지도 발생
		        new daum.Postcode({
		            oncomplete: function(data) { //선택시 입력값 세팅
		                document.getElementById("address").value = data.address; // 주소 넣기
		                document.querySelector("input[id=address_detail]").focus(); //상세입력 포커싱
		            }
		        }).open();
		    });
		}
}
function notAddress(){
	$("#address_detail").val("지역설정안함");
}

function registerAction(){


	var title      = document.getElementById("P_TITLE").value; 
	
	var category1      = document.getElementById("category1");
	var category2      = document.getElementById("category2");
	var category3      = document.getElementById("category3");
	
	var price      = document.getElementById("price").value;
	
	var explain    = document.getElementById("explain").value;
	
	var count      = document.getElementById("count").value; 
	
	
	if(title == ""){
		alert("제목을 입력해주세요!")
		return false;
	} else if(category1.value == "1"){
		alert("카테고리 대분류를 선택해주세요!")
		return false;
	} else if(category2.value == "1"){
		alert("카테고리 중분류를 선택해주세요!")
		return false;
	} else if(category3.value == "1"){
		alert("카테고리 소분류를 선택해주세요!")
		return false;
	} else if(price == ""){
		alert("가격을 입력해주세요!")
		return false;
	} else if(explain == ""){
		alert("설명을 입력해주세요!")
		return false;
	} else if(count == ""){
		alert("수량을 입력해주세요!")
		return false;
	} 
	
	
	if((count.keyCode > 48 && count.keyCode < 57 ) 
      || count.keyCode == 8 //backspace
      || count.keyCode == 37 || count.keyCode == 39 //방향키 →, ←
      || count.keyCode == 46 
      || count.keyCode == 39){
   	}else{
 	 count.returnValue=false;
   	}
	
	var form = $("#dataForm")[0];        
 	var formData = new FormData(form);
		for (var x = 0; x < filesArr.length; x++) {
			// 삭제 안한것만 담아 준다. 
			if(!filesArr[x].is_delete){
				 formData.append("article_file", filesArr[x]);
			}
		}
   /*
   * 파일업로드 multiple ajax처리
   */    
   console.log(form);
	$.ajax({
   	      type: "POST",
   	   	  enctype: "multipart/form-data",
   	      url: "/file-upload",
       	  data : formData,
       	  processData: false,
   	      contentType: false,
   	      success: function (data) {
   	    	if(JSON.parse(data)['result'] == "OK"){
   	    		alert("파일업로드 성공");
   	    		var moveurl= "/index";
   	    		location.replace(moveurl);
			} else
				alert("최소 한 개의 이미지를 추가해주세요!");
   	      },
   	      error: function (xhr, status, error) {
   	    	alert("최소 한 개의 이미지를 추가해주세요!");
   	     return false;
   	      }
   	    });
   	    return false;
	}