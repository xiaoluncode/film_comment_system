<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" placeholder="请输入名称查询" style="width: 300px"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="primary" @click="reset">重置</el-button>

    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" style="margin-bottom: 10px" @click="handleAdd">新增</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="name" label="名称" />
        <el-table-column  label="操作" width="160">
          <template #default="scope">
            <el-button  type="primary"  @click="handleEdit(scope.row)">编辑</el-button>
            <el-button  type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <div class="card">
        <el-pagination background layout="total,prev, pager, next"
                       v-model:page-size="data.pageSize" v-model:current-page="data.pageNum"
                       :total="data.total" @current-change="load" />
    </div>

    <el-dialog v-model="data.fromVisible" title="电影分类" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 20px">
        <el-form-item label="名称" >
          <el-input v-model="data.from.name" autocomplete="off" placeholder="请输入分类名称" />
        </el-form-item>
         
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.fromVisible = false">取消</el-button>
          <el-button type="primary" @click="save">
            保存
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
  import{reactive} from "vue";
  import request from "@/utils/request";
  import {ElMessage, ElMessageBox} from "element-plus";
  const data = reactive({
    tableData:[],
    total:0,
    pageNum:1,
    pageSize:10,
    name: null,
    fromVisible:false,
    from:{}
  })
  const load= () => {
    request.get('/category/selectPage',{
      params:{
        pageNum:data.pageNum,
        pageSize:data.pageSize,
        name:data.name
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

  const handleAdd = () => {
    data.from = {}
    data.fromVisible = true
  }

  const add = () => {
    request.post('/category/add',data.from).then(res => {
      console.log(res)
      if (res.code === '200') {
        load()
        data.fromVisible = false
        ElMessage.success('添加成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }

  const handleEdit = (row) => {
    data.from = JSON.parse(JSON.stringify(row))
    data.fromVisible = true
  }
  const del = (id) => {
    ElMessageBox.confirm('删除数据后无法恢复，您确认吗？','确认删除',{type:'warning'}).then(res => {
      request.delete('/category/delete/' + id).then(res => {
        if (res.code === '200') {
          load()
          ElMessage.success('删除成功')
          data.fromVisible = false
        }else {
          ElMessage.error(res.msg)
        }
})
    }).catch(err => {})
  }
  const update = () => {
    request.put('/category/update',data.from).then(res => {
      if (res.code === '200') {
        load()
        data.fromVisible = false
        ElMessage.success('更新成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
  const save = () => {
    data.from.id ? update() : add()
  }



</script>
