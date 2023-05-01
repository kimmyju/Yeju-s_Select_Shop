//어느 함수에서든지 가져다 쓸수있게 선언하는 부분
let targetId;

//이 아래 중괄호 안에다가 함수를 호출하면 내가 페이지 접속할때마다 또는 새로고침 할 때마다 여기 있는 모든게 실행됨
$(document).ready(function (){
    $('#query').on('keypress',function(e){
        if(e.key=='Enter'){
            execSearch();
        }
    });
    $('#close').on('click',function(){
        $('#container').removeClass('active');

    })

    $('.nav div.nav-see').on('click',function(){
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click',function(){
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();

    showProduct();//관심목록 불러와서 보여주는 함수
})

function numberWithCommas(x){
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
}


function execSearch(){

    //클릭하고 이 함수로 넘어올때 그 전에 있던 검색 결과들을 지워주어야 함
    $('#search-result-box').empty();
    //1.검색창의 입력값을 가지고 온다
    let query =$('#query').val();
    //2.검색창 입력값을 검사하고,입력하지 않았을 경우 focus
    if(query=='') {
        alert("검색어를 입력해주세요");
        $('#query').focus(); //커서 깜빡이는거
        //3.GET/api/search?query${query} 요청
    }
    $.ajax({
        type:'GET',
        url: `/api/search?query=${query}`,
        success: function(response){//response itemdto의리스트가 들어가 있을거임
            for(let i=0; i <response.length;i++){
                let itemDto = response[i];
                let tempHtml=addHTML(itemDto); //html로 바꿔줌
                $('#search-result-box').append(tempHtml);//append
            }
        }
        })

        //4.for문마다 itemDto를 꺼내서 html만들고 검색결과 목록에 붙이기
}


function addHTML(itemDto){
    //class="search-itemDto"인 녀석에서
    //image,title,lprice,addProduct활용하기
    //참고> onclick = 'addProduct(${JSON.stringify(itemDto)})'

    return `<div class="search-itemDto">
        <div class="search-itemDto-left">
            <img src="${itemDto.image}" alt=''>
        </div>
        <div class="search-itemDto-center">
            <div>${itemDto.title}</div>
            <div class="price">
                ${numberWithCommas(itemDto.lprice)}
                <span class="unit">원</span>
            </div>
        </div>
        <div class="search-itemDto-right">
            <img src="images/icon-heart1.png" alt="" onClick='addProduct(${JSON.stringify(itemDto)})'> 
        </div>
    </div>`;

}
//itemDto가 json이고 괄호안에는 문자열이 들어가야 하므로 스트링형태로 바꿔줌-82번줄
function addProduct(itemDto){//참고: 자바 스크립크는 이렇게 json형태의 문자열을 받으면 다시 자동으로 json으로 바꾸어줌
    //1. Post /api/products에 관심 상품 요청
    $.ajax({
        type: "POST",
        url: "/api/products",
        contentType: "application/json",//데이터 원형을 알기 위해서 알려주는 애
        data: JSON.stringify(itemDto), //data를 보낼땐 문자열화 해주어야 하므로
        success: function(response)//요청 성공시 어떻게 할건지
        {
            $('#container').addClass('active'); //container은 최저가 팝업창의  id
            targetId = response.id;

        }
    })

    //2. 응답 함수에서 modal을 뜨게 하고,targetId를 response.id로 설정(숙제로 myprice를 설정하기 위함)
}

function showProduct(){
    //* 관심 상품 목록: #product-container(id가)
    //*검색 결과 목록: #search-result-box
    //* 관심상품 html 만드는 함수: addProductItem

    //1.GET/api/products요청
    $.ajax({
        type:'GET',
        url:'/api/products',
        success:function(response){//응답에 성공을 했을 때 이 함수 실행,response안에는 결과값이 들어있음
            //2.관심상품 목록,검색결과 목록비우기
            $('#product-container').empty(); //담아놓은 검색 결과 삭제
            $('#search-result-box').empty();//기존 검색결과도 삭제
            //3.for문마다 관심상품 html만들어서 관심상품 목록 띄우기
            for(let i =0; i < response.length;i++) {
                let product = response[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml);
            }
        }
    })

}


function addProductItem(product)
{

    return `<div class="product-card" onclick="window.location.href='${product.link}'">
        <div class="card-header">
            <img src="${product.image}" alt="">
        </div>
        <div class="card-body">
            <div class="title">
                ${product.title}
            </div>
            <div class="lprice">
                <span>${numberWithCommas(product.lprice)}</span>원
            </div>
            <div class="isgood ${product.lprice > product.myprice ? 'none' : ''}"> 
                최저가
            </div>
        </div>
      </div>`;

}

function setMyprice() {
    let myprice = $('#myprice').val();



    //2.검색창 입력값을 검사하고,입력하지 않았을 경우 focus
   if(myprice == '') {
       alert("최저가를 입력해 주세요");
        return;
    }


        $.ajax({
            type: "PUT",
            contentType: "application/json",//데이터 원형을 알기 위해서 알려주는 애
            data: JSON.stringify({myprice: myprice}), //data를 보낼땐 문자열화 해주어야 하므로
            url: `/api/products/${targetId}`,
            success: function(response) {//response itemdto의리스트가 들어가 있을거임
                $('#container').removeClass('active');
                alert("설정이 완료되었습니다.");
                window.location.reload();
            }
        })




}



