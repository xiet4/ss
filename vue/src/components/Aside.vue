<script>
export default {
  name: "Aside",
  props:{

  }
}
</script>

<template>
  <el-menu :default-openeds="['1', '3']"
    router>
    <el-menu-item index="/"><i class=" el-icon-house"></i>主页</el-menu-item>

    <el-submenu >
      <template slot="title"><i class="el-icon-setting"></i>系统管理</template>
      <el-menu-item index="/user">人员管理</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<style scoped>

</style>