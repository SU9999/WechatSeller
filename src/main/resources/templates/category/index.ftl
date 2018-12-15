<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#-- 边栏slidebar -->
    <#include "../common/nav.ftl">
    <#-- 主要内容Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>类目名称</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目Type</label>
                            <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}"/>
                        </div>
                        <#-- 将productId设置为隐藏域，便于修改操作使用 -->
                        <input type="hidden" name="categoryId" value="${(category.categoryId)!'-1'}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
