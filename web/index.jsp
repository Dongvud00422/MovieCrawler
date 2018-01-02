<%@ page import="com.Crawler.entity.Movie" %>
<%@ page import="com.Crawler.entity.gson.ListResponseMovies" %>
<%@ page import="com.untility.RestfulHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ListResponseMovies listMovies = RestfulHelper.gson.fromJson(String.valueOf(request.getAttribute("listMovie")), ListResponseMovies.class);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>"2 3 CỐC" Cinema</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/index.css" type="text/css">
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
                <a class="navbar-brand" href="/"><img src="img/icon-logo.png" style="width: 40%"></a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-center menu_item">
                    <li class="active"><a href="/">Trang chủ</a></li>
                    <li><a href="/search">Tìm kiếm</a></li>
                </ul>
            </div>

        </div>
    </nav>
</header>


<section class="container">

    <!-- SHOW SLIDE FILM -->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">

        <div class="carousel-inner">

            <div class="item active">
                <img src="img/slide-phim2.jpg" style="width:100%;">
            </div>
            <div class="item">
                <img src="img/slide-phim1.jpg" style="width:100%;">
            </div>
            <div class="item">
                <img src="img/slide-phim3.jpg" style="width:100%;">
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
    <div class="event">
        <div class="row ">
            <div class="col-md-4">
                <hr>
            </div>
            <div class="col-md-5">
                <img src="img/event.gif">
            </div>
            <div class="col-md-3">
                <hr>
            </div>
        </div>

        <div class="row ">
            <div class="col-md-4"></div>
            <div class="col-md-5 event-content">
                <a href="#">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <img src="img/event2.png">
                </a>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row ">
            <div class="col-md-4"></div>
            <div class="col-md-5 event-content">
                <a href="#">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <img src="img/event3.png">
                </a>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row ">
            <div class="col-md-4"></div>
            <div class="col-md-5 event-content">
                <a href="#">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <img src="img/event4.png">
                </a>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row ">
            <div class="col-md-4"></div>
            <div class="col-md-5 event-content">
                <a href="#">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <img src="img/event5.png">
                </a>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row ">
            <div class="col-md-4"></div>
            <div class="col-md-5 event-content">
                <button type="button" id="btn-show" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Tìm Kiếm Chi Tiết
                </button>


            </div>
            <div class="col-md-3"></div>
        </div>
        <br>

        <!-- search filmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm -->
        <div id="searchFilm" style="display: none">
            <form class="container form-horizontal formSearch">

                <div class="form-group row">
                    <div class="col-md-4"></div>
                    <label class="col-md-1" for="movieName">Tên Phim</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="movieName" placeholder="Phim cần tìm">
                    </div>
                    <button id="full-text-search" type="button" class="btn btn-primary btn-search">Tìm</button>
                </div>
                <div class="form-group row">
                    <div class="col-md-4"></div>
                    <label class="col-md-1">Giờ chiếu</label>
                    <div class="col-md-6" id="time">
                        <div class="col-md-2">
                            <input type="text" class="form-control" id="from-hour" placeholder="Giờ">
                        </div>
                        <div class="col-md-2 ">
                            <input type="text" class="form-control" id="from-min" placeholder="Phút">
                        </div>
                        <label class="col-md-1"> Đến</label>
                        <div class="col-md-2 fix-time">
                            <input type="text" class="form-control" name="to-hour" placeholder="Giờ">
                        </div>
                        <div class="col-md-2 ">
                            <input type="text" class="form-control" name="to-min" placeholder="Phút">
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-4"></div>
                    <label class="col-md-1">Thể loại</label>
                    <div class="col-md-5" id="category">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="1">Hành động
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="2">Kinh Dị
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="3">Tình cảm
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="4">Tâm Lý
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="5">Hài
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="6">Hoạt Hình
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="category" value="7">Gia đình
                        </label>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-5"></div>
                    <button type="button" class="btn btn-primary btn-search" id="search-detail">Tìm kiếm</button>
                    <button type="reset" class="btn btn-primary">Reset</button>
                </div>
            </form>

            <section class="container">
                <div class="row result">
                    <div class="col-md-4">
                        <hr>
                    </div>
                    <div class="col-md-5">
                        <img src="img/search-result.png">
                    </div>
                    <div class="col-md-3">
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3" id="search-result"></div>
                </div>
            </section>
        </div>
    </div>

    <!-- tạo modal trailer phim -->
    <div id="myModal" class="modal">
        <span class="close">&times;</span>
        <iframe class="modal-content" id="img01" width="560" height="415"
                src="https://www.youtube.com/embed/t0l0NIyZRrM" frameborder="0" gesture="media"
                allow="encrypted-media" allowfullscreen></iframe>
    </div>

    <!-- 		Danh sách các phim -->
    <div class="list-film">
        <div class="row ">
            <div class="col-md-4">
                <hr>
            </div>
            <div class="col-md-5">
                <img src="img/title-list-film.png">
            </div>
            <div class="col-md-3">
                <hr>
            </div>
        </div>
        <div class="row">
            <% for (Movie movie : listMovies.getDataMovie()) { %>
            <div class="col-md-3 view ">
                <a href="http://localhost:8080/movie/showtime?movieId=<%=movie.getId()%>"><img src="<%=movie.getPoster()%>" style="width: 95%"></a>
                <span class="type"><%=movie.getType()%> - <%=movie.getMinAge()%> </span>
                <a href="#"><img src="img/btn-play.png" class="play-trailer"></a>
                <button class="detail" type="button">Chi Tiết</button>
                <a href="#"><h4><%=movie.getName()%>
                </h4></a>
                <span><%=movie.getDuration()%> phút</span>
                <br>
                <span>Khởi chiếu <%=movie.getOpenAt()%></span>
            </div>
            <% } %>
        </div>
    </div>

    <!-- phân trang -->
    <div class="row">
        <div class="col-md-4 col-sm-4 col-xs-4"></div>
        <div class="col-md-4 col-sm-4 col-xs-8">
            <center>
                <ul class="pagination">
                    <%for (int i = 1; i <= listMovies.getTotalPage(); i++) {%>
                    <%if (i > 2) {%>
                    <li><a href="#">&laquo;</a></li>
                    <%}%>
                    <li><a href="#" <%if (i==listMovies.getPage()){%>class="active"<%}%>><%=i%>
                    </a></li>
                    <%if (i > 2) {%>
                    <li><a href="#">&raquo;</a></li>
                    <%}%>
                    <%}%>
                </ul>

            </center>
        </div>
        <div class="col-md-4 col-sm-4 "></div>
    </div>
</section>


<!-- chuyển lên đầu trang -->
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
<script>

    document.getElementById("full-text-search").onclick = function (ev) {
        $.ajax({
            url: 'http://localhost:8080/_api/v1/search?name=' + document.getElementById("movieName").value,
            type: 'GET',
            dataType: 'json',
            success: function (data, textStatus, xhr) {
                var content = '';
                if (data.length === 0) {
                    content += "<center>Không có kết quả<center>";
                }
                for (var i = 0; i < data.length; i++) {
                    content += "<a href='#'><img src='" + data[i].poster + "' style='width: 95%'></a>" +
                        "                <span class='type'>" + data[i].type + " - " + data[i].minAge + "</span>" +
                        "            <a href='#'><h4>" + data[i].name + "</h4></a>" +
                        "<span>" + data[i].duration + " phút</span>" +
                        "<br><span>Khởi chiếu: " + data[i].openAt + "</span>";
                }
                document.getElementById("search-result").innerHTML = content;
            },
            error: function (xhr, textStatus, errorThrown) {
                console.log('Error in Database');
            }
        });
    };

    document.getElementById("search-detail").onclick = function (ev) {

        var selected = [];
        $('#category input:checked').each(function () {
            selected.push($(this).attr('value'));
        });

        var c = document.getElementById("time");
        var fromHour = c.getElementsByTagName("input")[0].value;
        var fromMin = c.getElementsByTagName("input")[1].value;
        var toHour = c.getElementsByTagName("input")[2].value;
        var toMin = c.getElementsByTagName("input")[3].value;
        var startMilis = '';
        var endMilis = '';
        if (!isNaN(fromHour)) {
            alert('not a number');

        } else {
            var currentdate = new Date();
            var startTime = currentdate.getDate() + "/"
                + (currentdate.getMonth() + 1) + "/"
                + currentdate.getFullYear()
                + " " + fromHour + ":" + fromMin + ":00";
            var endTime = currentdate.getDate() + "/"
                + (currentdate.getMonth() + 1) + "/"
                + currentdate.getFullYear()
                + " " + toHour + ":" + toMin + ":00";

            var date = new Date(startTime);
            startMilis = date.getTime();
            date = new Date(endTime);
            endMilis = date.getTime();
        }


        alert('http://localhost:8080/_api/v1/search?category=' + selected + "&startTime=" + startMilis + "&endTime=" + endMilis);
        $.ajax({
            url: 'http://localhost:8080/_api/v1/search?category=' + selected + "&startTime=" + startMilis + "&endTime=" + endMilis,
            type: 'GET',
            dataType: 'json',
            success: function (data, textStatus, xhr) {
                var content = '';
                if (data.length === 0) {
                    content += "<center>Không có kết quả<center>";
                }
                for (var i = 0; i < data.length; i++) {
                    content += "<a href='#'><img src='" + data[i].poster + "' style='width: 95%'></a>" +
                        "                <span class='type'>" + data[i].type + " - " + data[i].minAge + "</span>" +
                        "            <a href='#'><h4>" + data[i].name + "</h4></a>" +
                        "<span>" + data[i].duration + " phút</span>" +
                        "<br><span>Khởi chiếu: " + data[i].openAt + "</span>";
                }
                document.getElementById("search-result").innerHTML = content;
            },
            error: function (xhr, textStatus, errorThrown) {
                console.log('Error in Database');
            }
        });
    };


    // check thành phố
    function choice(city) {
        var arr = city.split("_");
        var chosse = "";
        for (var index in arr) {
            chosse += arr[index] + " ";
        }
        document.getElementById("city").innerHTML = chosse.trim() + "  <span class=\"caret\"></span>";

        var url = document.getElementsByClassName("fix");
        for (var i in url) {
            var sp = url[i].href.split("cityName=");
            url[i].href = sp[0] + "cityName=" + chosse.trim();
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

    // show form search
    $("#btn-show").click(function () {
        $("#searchFilm").toggle(300);
    });

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

<script src="js/bootstrap.min.js"></script>

</body>
</html>