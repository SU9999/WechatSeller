<html>
<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>订单Id</th>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>地址</th>
                    <th>金额</th>
                    <th>订单状态/th>
                    <th>支付状态</th>
                    <th>创建时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTOPage.content as orderDTO>
                 <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.buyerName}</td>
                    <td>${orderDTO.buyerPhone}</td>
                    <td>${orderDTO.buyerAddress}</td>
                    <td>${orderDTO.orderAmount}</td>
                    <td>${orderDTO.getOrderStatusEnum().msg}</td>
                    <td>${orderDTO.getPayStatusEnum().msg}</td>
                    <td>${orderDTO.createTime}</td>
                    <td>
                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                    </td>
                    <td>
                        <#if orderDTO.orderStatus == 0>
                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                        </#if>
                    </td>
                  </tr>
                </#list>
                </tbody>
            </table>
            <ul class="pagination pagination-sm pull-right">
                <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                <#else>
                    <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${currentSize}">上一页</a></li>
                </#if>
                <#list 1..orderDTOPage.getTotalPages() as index>
                <#--    <#if currentPage-index gte 3>
                        <li><a href="/sell/seller/order/list?page=${index}&size=${currentSize}">...</a></li>
                    </#if>

                    <#if index-currentPage gte 3>
                        <li><a href="/sell/seller/order/list?page=${index}&size=${currentSize}">...</a></li>
                    </#if>-->

                    <#if currentPage == index>
                        <li class="disabled"><a href="#">${index}<br></a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${index}&size=${currentSize}">${index}<br></a></li>
                    </#if>

                </#list>
                <#if currentPage gte orderDTOPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else>
                    <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${currentSize}">下一页</a></li>
                </#if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
