<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-7">
                <img src="<c:url value="/image/home.jpg" />" style="height: 300px; width: 300px" alt=""/>
            </div>
            <div class="col-md-5">
                <h1>Hello World!!</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-sm-4 mb-2">
                <div class="card h-100 mb-4">
                    <div class="card-body bg-dark">
                        <h5 class="card-title text-warning">
                            <c:out value="${product.name}"/>
                        </h5>
                        <p class="card-text text-light">
                            <c:out value="${product.description}"/>
                        </p>
                        <p class="card-text text-light">
                            Price: $
                            <c:out value="${product.price}"/>
                        </p>
                        <a href="#" class="card-link btn btn-outline-info">
                            Add to Cart
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%@include file="includes/footer.jsp"%>