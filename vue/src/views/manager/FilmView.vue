
<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" placeholder="请输入电影名称查询" style="width: 300px"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="primary" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button :class="{'film-active': data.categoryId == null}" @click="loadFilmByCategory(null)">全部</el-button>
      <el-button :class="{'film-active': data.categoryId == item.id}" v-for="item in data.categoryList" :key="item.id" @click="loadFilmByCategory(item.id)" >{{item.name}}</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in data.tableData" :key="item.id" style="margin-bottom: 20px;cursor:pointer"  @click="goDetail(item.id)">
          <img :src="item.cover" alt="" style="width: 100% ; height: 400px;border-radius: 5px">
          <div style="margin: 5px 0; font-size: 18px" class="line1">{{item.name}}</div>
          <div style="margin: 5px 0; display: flex; align-items: center">
            <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
            <div style="flex: 1; text-align: right;color: #1967e3">{{item.commentNum}}人评论</div>
          </div>
          <div style="font-size: 13px;color: #666666" class="line3">{{item.descr}}</div>
        </el-col>
      </el-row>
    </div>
    <div class="card">
      <el-pagination background layout="total,prev, pager, next"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum"
                     :total="data.total" @current-change="load" />
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import request from "@/utils/request";


const data = reactive({
  name: null,
  pageNum: 1,
  pageSize: 8,
  tableData: [],
  total: 0,
  categoryList: [],
  categoryId: null
})
// 加载分类
const loadCategory = () => {
  request.get('/category/selectAll').then(res => {
    data.categoryList = res.data
  })
}
loadCategory()
// 加载分类下的电影
const loadFilmByCategory = (categoryId) => {
  data.categoryId = categoryId
  load()
}
// 加载电影
const load = () => {
  request.get('/film/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name:data.name,
      categoryId:data.categoryId
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()

const reset = () => {
  data.name = null
  load()
}

const goDetail = (id) => {
  location.href = '/filmDetail?id=' + id; // 添加等号
}

</script>

<style scoped>
.film-active{
  background-color: rgba(74, 122, 226, 0.51);
  color: white;
}
</style>