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
      user: JSON.parse(localStorage.getItem("user")),
      currentPath:''
    }
  },

  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")

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
        <el-dropdown-item> <router-link to="/inform" >个人信息 </router-link></el-dropdown-item>
        <el-dropdown-item><span @click="logout">退出 </span></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <span>{{user.username}}</span>
  </div>
</template>

<style scoped>

</style>