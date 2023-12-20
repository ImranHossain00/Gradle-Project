<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-6">
                <img src="<c:url value="/image/home.jpg" />" style="height: 200px" alt=""/>
            </div>
            <div class="col-md-6">
                <h1>Hello World!!</h1>
            </div>
        </div>
<%--    </div>--%>
<%--    <div class="row">--%>
<%--        <c:forEach var="product" items="${products}">--%>
<%--            <div class="col-sm-4">--%>
<%--                <div class="card h-100 mb-4">--%>
<%--                    <div class="card-body">--%>
<%--                        <h5 class="card-title">--%>
<%--                            <c:out value="${product.name}"/>--%>
<%--                        </h5>--%>
<%--                        <p class="card-text">--%>
<%--                            <c:out value="${product.description}"/>--%>
<%--                        </p>--%>
<%--                        <p class="card-text">--%>
<%--                            Price: $--%>
<%--                            <c:out value="${product.price}"/>--%>
<%--                        </p>--%>
<%--                        <a href="#" class="card-link btn btn-outline-info">--%>
<%--                            Add to Cart--%>
<%--                        </a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </c:forEach>--%>
    </div>
</div>
<%@include file="includes/footer.jsp"%>