<script>
export default {
  name: "Header",
  watch:{
    '$route':function (){
      this.currentPath = localStorage.getItem("currentPath")
    }
  },
  data(){
    return{
      currentPath:''
    }

  }
}
</script>

<template>
  <div style="display: flex;align-items:center">
    <div style="flex:1">
    <el-breadcrumb>
      <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{currentPath}}</el-breadcrumb-item>
    </el-breadcrumb>
    </div>
    <el-dropdown>
      <i class="el-icon-setting" style="margin-right: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>查看</el-dropdown-item>
        <el-dropdown-item>新增</el-dropdown-item>
        <el-dropdown-item>删除</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <span>王小虎</span>

  </div>
</template>

<style scoped>

</style>