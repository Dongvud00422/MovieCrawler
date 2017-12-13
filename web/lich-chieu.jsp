<%--
  Created by IntelliJ IDEA.
  User: Nam Nguyen
  Date: 12/6/2017
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lịch chiếu phim</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/lich-chieu.css">
</head>
<body>
<header class="menu">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"><img src="img/icon-logo.png" style="width: 40%"></a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-center menu_item">
                    <li><a href="index.jsp">Phim</a></li>
                    <li class="active"><a href="#">Lịch chiếu</a></li>
                    <li><a href="#">Cụm Rạp</a></li>
                    <li><a href="#">Ứng dụng</a></li>

                </ul>

                <form class="navbar-form navbar-right">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Nhập từ khóa cần tìm">
                    </div>
                    <button type="submit" class="btn btn-default">Tìm kiếm</button>
                </form>

                <%--<%--%>
                <%--Gson gsonCity = new Gson();--%>
                <%--ListResponseCity listCity = gsonCity.fromJson((String) request.getAttribute("listCity"), ListResponseCity.class);--%>
                <%--%>--%>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown"><a id="city" class="dropdown-toggle" data-toggle="dropdown" href="#">Thành phố
                        hiện tại<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <%--<%for (City cityName : listCity.getDataCity()) {%>--%>
                            <%--<li>--%>
                            <%--<a href="#" onclick=choice('<%=cityName.getName()%>_""')><%=cityName.getName()%>--%>
                            <%--</a></li>--%>
                            <%--<%}%>--%>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<div>
    <img class="background blur" src="img/slide-phim1.jpg" style="width: 100%">
    <div class="container view_film">
        <div class="row">
            <div class="col-md-3">
                <img id="myImg" src="img/dc-phim2.jpg" style="width: 100%">
            </div>
            <div class="col-md-7 view">
                <span>17.11.2017</span><br>
                <span class="type">C16</span><span>  The Mountain Between Us - Ngọn Núi Giữa Hai Ta</span><br>
                <span>Thể loại: Phiêu lưu, tâm lý, tình cảm</span><br>
                <span>120 phút - 7.5 IMDb - 2D/3D/Digital/IMAX</span><br>
            </div>
        </div>
    </div>
</div>

<%-------------------tao modal trailer---------------------------%>

<div id="myModal" class="modal">
    <span class="close">&times;</span>
    <iframe  class="modal-content" id="img01" width="560" height="315" src="https://www.youtube.com/embed/t0l0NIyZRrM" frameborder="0" gesture="media"
            allow="encrypted-media" allowfullscreen></iframe>
</div>

<!----------------------------button chuyển lên đầu trang---------------------------------->
<button onclick="topFunction()" id="myBtn" title="Go to top">Đầu trang</button>

<!-------------------------------footer----------------------->


<div class="panel-footer">
    <div class="container footer">
        <hr>
        <section>
            <div class="row">
                <div class="col-md-3">
                    <p>ĐỐI TÁC</p>
                    <div class="row iconDT">
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon1.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon2.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon3.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon4.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon5.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon6.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon7.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="../img/icon_doitac/icon8.png"
                                                               style="width: 60%"></a></div>
                    </div>
                </div>
                <div class="col-md-2">
                    <p>TEAM PROJECT</p>
                    <p>Vũ Đức Đông</p>
                    <p>Trần Quốc Toản</p>
                    <p>Nguyễn Duy Nam</p>
                    <p>Nguyễn Thanh Hân</p>
                    <p>Hoàng Đình Toàn</p>
                </div>
                <div class="col-md-3">
                    <p>FPT APTECH HÀ NỘI</p>
                    <p>Đại học FPT, Số 8, Tôn Thất Thuyết, Mỹ Đình, Từ Liêm</p>
                    <p>Điện thoại:(024) 7300 8855 - (024) 6656 5662</p>
                    <p>Email: aptech.hn@fpt.edu.vn</p>
                    <p>Hotline: 0976 199 772 </p>
                </div>
                <div class="col-md-2">
                    <p>MOBILE APP</p>
                    <div class="row iconDT">
                        <div class="col-md-5">
                            <a href="#">
                                <img src="img/icon_doitac/icon-android.png" style="width: 70%;">
                            </a>
                        </div>
                        <div class="col-md-5">
                            <a href="#">
                                <img src="img/icon_doitac/icon-apple.png" style="width: 70%;">
                            </a>
                        </div>

                    </div>

                </div>
                <div class="col-md-2">
                    <p>SOCIAL</p>
                    <div class="row iconDT">
                        <div class="col-md-5">
                            <a href="#">
                                <img src="img/icon_doitac/if_social_facebook_box_white_60824.png" style="width: 70%;">
                            </a>
                        </div>
                        <div class="col-md-5">
                            <a href="#">
                                <img src="img/icon_doitac/logo-zalo.png" style="width: 70%;">
                            </a>
                        </div>

                    </div>
                </div>
            </div>
        </section>
    </div>
</div>


<script>

    //    ---------------------check thành phố----------------------------------
    function choice() {
        alert(city);
        document.getElementById("city").innerHTML = "" + "<span class='caret'></span>";
    }

    //-----------------------chuyển lên đầu trang----------------------

    window.onscroll = function () {
        scrollFunction()
    };

    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            document.getElementById("myBtn").style.display = "block";
        } else {
            document.getElementById("myBtn").style.display = "none";
        }
    }

    // When the user clicks on the button, scroll to the top of the document
    function topFunction() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }

    //----------------------Hiện thị trailer phim-------------
    // Get the modal
    var modal = document.getElementById('myModal');

    // Get the image and insert it inside the modal - use its "alt" text as a caption
    var img = document.getElementById('myImg');
    var modalImg = document.getElementById("img01");
    var captionText = document.getElementById("caption");
    img.onclick = function () {
        modal.style.display = "block";

    }

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    }
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
