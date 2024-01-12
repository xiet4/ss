<template>
  <div>
    <el-container style="height: 100% ; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);height: 100%">
        <Aside></Aside>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
        <Header></Header>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>

      </el-container>

    </el-container>
  </div>

</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import request from "@/utils/request";

export default {
  name: 'HomeView',

  components:{
    Aside,
    Header
  },


}

</script>
