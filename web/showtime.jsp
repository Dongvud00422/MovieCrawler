<%@ page import="com.Crawler.entity.Movie" %>
<%@ page import="com.Crawler.entity.gson.ListResponseMovies" %>
<%@ page import="com.untility.RestfulHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ListResponseMovies listMovies = RestfulHelper.gson.fromJson(String.valueOf(request.getAttribute("listMovie")), ListResponseMovies.class);
    Movie movie = (Movie) request.getAttribute("movie");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lịch Chiếu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/lich-chieu.css" type="text/css">
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
                    <li class="active"><a href="#">Phim</a></li>
                    <li><a href="#">Lịch chiếu</a></li>
                    <li><a href="#">Cụm Rạp</a></li>
                    <li><a href="#">Ứng dụng</a></li>
                </ul>
            </div>

        </div>
    </nav>
</header>
<div>
    <img class="background blur" src="<%=movie.getPoster()%>" style="width: 100%">
    <div class="container view_film">
        <div class="row">
            <div class="col-md-3">
                <img id="myImg" src="<%=movie.getPoster()%>" style="width: 100%">
            </div>
            <div class="col-md-7 view">
                <span>Khởi chiếu: <%=movie.getOpenAt()%></span><br>
                <span class="type"><%=movie.getMinAge()%></span><span><%=movie.getName()%></span><br>
                <span>Thể loại: <%=movie.getCategory()%></span><br>
                <span><%=movie.getDuration()%> phút - <%=movie.getType()%></span><br>
            </div>
        </div>
    </div>
</div>

<div id="myModal" class="modal">
    <span class="close">&times;</span>
    <iframe  class="modal-content" id="img01" width="560" height="315" src="https://www.youtube.com/embed/t0l0NIyZRrM" frameborder="0" gesture="media"
             allow="encrypted-media" allowfullscreen></iframe>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4 menu2">
            <a href="#" class="lc">Lịch Chiếu</a>
            <a href="#" class="tt">Thông Tin</a>
        </div>
        <div class="col-md-4"></div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-3 theater">
            <a href="#">
                <div class="row ">
                    <div class="col-md-5">
                        <img class="icon" src="img/lich-chieu/rap-phim/CGV.png" style="width: 90%">
                    </div>
                    <div class="col-md-7"><p class="size-rap">CGV Cinemas</p></div>
                </div>
            </a>
            <hr>
            <a href="#">
                <div class="row">
                    <div class="col-md-5">
                        <img class="icon" src="img/lich-chieu/rap-phim/lotte.png" style="width: 70%">
                    </div>
                    <div class="col-md-7"><p class="size-rap">Lotte Cinema</p></div>
                </div>
            </a>
            <hr>
            <a href="#">
                <div class="row">
                    <div class="col-md-5">
                        <img class="icon" src="img/icon_doitac/icon4.png" style="width: 70%">
                    </div>
                    <div class="col-md-7"><p class="size-rap">Galaxy Cinema</p></div>
                </div>
            </a>
            <hr>
            <a href="#">
                <div class="row">
                    <div class="col-md-5">
                        <img class="icon" src="img/icon_doitac/icon2.png" style="width: 80%">
                    </div>
                    <div class="col-md-7"><p class="size-rap">Mega GS</p></div>
                </div>
            </a>
            <hr>
            <a href="#">
                <div class="row">
                    <div class="col-md-5">
                        <img class="icon" src="img/icon_doitac/icon3.png" style="width: 70%">
                    </div>
                    <div class="col-md-7"><p class="size-rap">CineStar</p></div>
                </div>
            </a>
            <hr>
            <a href="#">
                <div class="row">
                    <div class="col-md-5">
                        <img class="icon" src="img/icon_doitac/iconx.png" style="width: 70%">
                    </div>
                    <div class="col-md-7"><p class="size-rap">Cụm Rạp Khác</p></div>
                </div>
            </a>
            <hr>
        </div>
        <div class="col-md-7 open-at">
            <div class="row " >
                <a href="#" >
                    <div class="col-md-1">
                        <p class="day">T6</p>
                        <p class="day">29</p>
                    </div>
                </a>
                <a href="#">
                    <div class="col-md-1">
                        <p class="day">T7</p>
                        <p class="day">30</p>
                    </div>
                </a>
                <a href="#">
                    <div class="col-md-1">
                        <p class="day">CN</p>
                        <p class="day">31</p>
                    </div>
                </a>
                <a href="">
                    <div class="col-md-1">
                        <p class="day">T2</p>
                        <p class="day">01</p>
                    </div>
                </a>
                <a href="">
                    <div class="col-md-1">
                        <p class="day">T3</p>
                        <p class="day">02</p>
                    </div>
                </a>
                <a href="#">
                    <div class="col-md-1">
                        <p class="day">T4</p>
                        <p class="day">03</p>
                    </div>
                </a>
                <a href="#">
                    <div class="col-md-1">
                        <p class="day">T5</p>
                        <p class="day">04</p>
                    </div>
                </a>
            </div>
            <hr class="dayhr">
            <a href="#">
                <div class="row ">
                    <div class="col-md-2">
                        <img src="img/lich-chieu/cgv/vincom.jpg" style="width: 87%">
                    </div>
                    <div class="col-md-10">
                        <p class="address">CGV - Vincom Center Bà Triệu <br>
                            Tầng 6, Vincom City Towers, 191 Bà Triệu, Hai Bà Trưng Hà Nội <br>
                            23:35
                        </p>
                    </div>
                </div>
            </a>
            <hr class="dayhr">
            <a href="#">
                <div class="row ">
                    <div class="col-md-2">
                        <img src="img/lich-chieu/cgv/longbien.jpg" style="width: 80%">
                    </div>
                    <div class="col-md-10">
                        <p class="address">CGV - Aeon Long Biên <br>
                            Tầng 4, TTTM Aeon Long Biên, 27 Cổ Linh, Long Biên, Hà Nội <br>
                            23:20
                        </p>
                    </div>
                </div>
            </a>
            <hr >
            <a href="#">
                <div class="row ">
                    <div class="col-md-2">
                        <img src="img/lich-chieu/cgv/hoguom.jpg" style="width: 80%">
                    </div>
                    <div class="col-md-10">
                        <p class="address">CGV - Hồ Gươm Plaza <br>
                            Tầng 3, TTTM Hồ Gươm Plaza, 110 Trần Phú, Mỗ Lao, Hà Đông Hà Nội <br>
                            23:35  10:00  11:30
                        </p>
                    </div>
                </div>
            </a>
            <hr >
        </div>
    </div>
</div>

<!--button chuyển lên đầu trang-->
<button onclick="topFunction()" id="myBtn" title="Go to top">Đầu trang</button>

<!-- footer -->
<div class="panel-footer">
    <div class="container footer">
        <hr>
        <section>
            <div class="row">
                <div class="col-md-3">
                    <p>ĐỐI TÁC</p>
                    <div class="row iconDT">
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon1.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon2.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon3.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon4.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon5.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon6.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon7.png"
                                                               style="width: 60%"></a></div>
                        <div class="col-md-3"><a href="#"><img src="img/icon_doitac/icon8.png"
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
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
    // check thành phố
    function choice(city) {
        var arr = city.split("_");
        var chosse = "";
        for (var index in arr){
            chosse+=arr[index]+" ";
        }
        document.getElementById("city").innerHTML = chosse.trim()+"  <span class=\"caret\"></span>";

        var url = document.getElementsByClassName("fix");
        for(var i in url){
            var sp = url[i].href.split("cityName=");
            url[i].href = sp[0]+"cityName="+chosse.trim();
        }
    }

    // chuyển lên đầu trang
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

    function topFunction() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }



    // modal trailer film
    var modal = document.getElementById('myModal');

    // Get the image and insert it inside the modal - use its "alt" text as a caption
    var img = document.getElementById('myImg');
    var modalImg = document.getElementById("img01");

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


</body>
</html>