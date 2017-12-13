<%@ page import="com.Crawler.entity.City" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.Crawler.entity.ListResponseCity" %>
<%@ page import="com.Crawler.entity.ListResponseMovies" %>
<%@ page import="com.Crawler.entity.Movie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Phim hay mỗi ngày</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="./css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="./css/style-index.css" type="text/css">

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
                    <li><a href="lich-chieu.jsp">Lịch chiếu</a></li>
                    <li><a href="#">Cụm Rạp</a></li>
                    <li><a href="#">Ứng dụng</a></li>

                </ul>

                <form class="navbar-form navbar-right">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Nhập từ khóa cần tìm">
                    </div>
                    <button type="submit" class="btn btn-default">Tìm kiếm</button>
                </form>

                <%
                    Gson gsonCity = new Gson();
                    ListResponseCity listCity = gsonCity.fromJson((String) request.getAttribute("listCity"), ListResponseCity.class);
                %>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown"><a id="city" class="dropdown-toggle" data-toggle="dropdown" href="#">Thành phố
                        hiện tại<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <%for (City cityName : listCity.getDataCity()) {%>
                            <li>
                                <a href="#" onclick=choice('<%=cityName.getName()%>_""')><%=cityName.getName()%>
                                </a></li>

                            <%}%>
                        </ul>
                    </li>
                </ul>

            </div>

        </div>
    </nav>
</header>
<div id="myCarousel" class="carousel slide hidden-xs" data-ride="carousel">
    <!-- Wrapper for slides -->
    <div class="carousel-inner">
        <div class="item active">
            <img src="img/slide-phim1.jpg" style="width:100%;">
        </div>
        <div class="item">
            <img src="img/slide-phim2.jpg" style="width:100%;">
        </div>

        <div class="item">
            <img src="img/slide-phim3.jpg" style="width:100%;">
        </div>
        <div class="item">
            <img src="img/slide-phim4.jpg" style="width:100%;">
        </div>
    </div>
    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<!---------------------------------------Phim đang chiếu------------------------------->
<div class="container">
    <h2>Phim Đang Chiếu</h2>
    <hr/>
    <% Gson gsonMovie = new Gson();
        ListResponseMovies listMovie = gsonMovie.fromJson((String) request.getAttribute("listMovie"), ListResponseMovies.class);
    %>
    <div class="row">
        <%
            for (Movie movie : listMovie.getDataMovie()) {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = sdf.parse("01/12/2017");
//            long millis = date.getTime();
//            System.out.println(millis);
//
//
//
//
//            long currentTime = System.currentTimeMillis();
//            if(millis < currentTime){


        %>
        <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6">

            <a href="lich-chieu.jsp"><img src="<%=movie.getPoster()%>" style="width:100%;"></a>
            <a href="lich-chieu.jsp"><h4><%=movie.getMovieName()%>
            </h4></a>
        </div>
        <%--<%}%>--%>
        <%}%>
    </div>
    <div class="row">
        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-4"></div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-8" >
            <ul class="pagination">
                <li class="disabled"><a href="#">&laquo;</a></li>
                <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-3 "></div>
    </div>

</div>

<!------------------------------------------Phim sắp chiếu----------------------------------->
<div class="container">
    <h2>Phim Sắp Chiếu</h2>
    <hr>

    <div class="row">
        <%for (Movie movie : listMovie.getDataMovie()) {%>
        <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6">
            <a href="#"><img src="<%=movie.getPoster()%>" style="width:100%;"></a>
            <a href="#"><h4><%=movie.getMovieName()%>
            </h4></a>
        </div>
        <%}%>

    </div>
    <div class="row">
        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-4"></div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-8" >
            <ul class="pagination">
                <li class="disabled"><a href="#">&laquo;</a></li>
                <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-3 "></div>
    </div>

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
</script>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
