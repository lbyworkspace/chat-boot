import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  getters:{
    total:state => state.total,
    account:state => state.account,
    currentAccount:state => state.total.find(v=>v.account == state.account),
    GlobalWs:state => state.GlobalWs,
    boxType:state => state.boxType,
    currentChatObj:state => state.currentChatObj
  },
  state: {
    total:[],
    account:undefined,
    GlobalWs:undefined,
    boxType:null,
    currentChatObj:null
  },
  mutations: {
    setTotal(state,total){
      state.total = total
    },
    setAccount(state,account){
      state.account = account
    },
    setGlobalWs(state,ws){
      state.GlobalWs = ws
    },
    setBoxType(state,type){
      state.boxType = type
    },
    setChatObj(state,obj){
      state.currentChatObj = obj
    }
  },
  actions: {
    login({commit},form){
      return new Promise((resolve) => {
        let ws = new WebSocket(`${process.env.VUE_APP_BASEURL.replace('http','ws')}/Global/${form.account}`)
        ws.onmessage = function(res){
          let result = JSON.parse(res.data)
          console.log(`当前在线用户`,result)
          commit('setTotal',result)
        }
        commit('setGlobalWs',ws)
        commit('setAccount',form.account)
        resolve()
      })
    },
    logout({commit,state}){
      return new Promise((resolve) => {
        state.GlobalWs.close()
        commit('setAccount',undefined)
        commit('setTotal',[])
        commit('setBoxType',null)
        commit('setTotal',[])
        commit('setChatObj',null)
        resolve()
      })
    },
    toChatroom({commit},p){
      commit('setBoxType',null)
      setTimeout(() => {
        commit('setBoxType',p.type)
      }, 500);
      commit('setChatObj',p.ob)
    }
  },
  modules: {
  }
})
