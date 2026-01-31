export default defineAppConfig({
  pages: [
    'pages/index/index',
    'pages/search/index',
    'pages/merchant/index',
    'pages/login/index',
    'pages/cart/index',
    'pages/checkout/index',
    'pages/order/list',
    'pages/order/detail',
    'pages/address/index',
    'pages/address/add',
    'pages/profile/index'
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#fff',
    navigationBarTitleText: 'FairFood',
    navigationBarTextStyle: 'black',
    backgroundColor: '#f5f5f5'
  },
  tabBar: {
    color: '#999999',
    selectedColor: '#ff4d4f',
    backgroundColor: '#ffffff',
    borderStyle: 'black',
    list: [
      {
        pagePath: 'pages/index/index',
        text: '首页',
        iconPath: 'images/tab/home.png',
        selectedIconPath: 'images/tab/home-active.png'
      },
      {
        pagePath: 'pages/order/list',
        text: '订单',
        iconPath: 'images/tab/order.png',
        selectedIconPath: 'images/tab/order-active.png'
      },
      {
        pagePath: 'pages/profile/index',
        text: '我的',
        iconPath: 'images/tab/profile.png',
        selectedIconPath: 'images/tab/profile-active.png'
      }
    ]
  },
  permission: {
    'scope.userLocation': {
      desc: '获取位置信息以展示附近商家'
    }
  }
})
