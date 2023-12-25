<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
        <div class="jumbotron shadow p-5 mb-3 mt-3 rounded">
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
                <div class="card h-100 shadow rounded">
                    <div class="card-body bg-body">
                        <h5 class="card-title text-muted">
                            <c:out value="${product.name}"/>
                        </h5>
                        <p class="card-text text-dark-emphasis">
                            <c:out value="${product.description}"/>
                        </p>
                        <p class="card-text text-dark-emphasis">
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