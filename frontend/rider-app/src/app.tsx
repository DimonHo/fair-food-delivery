import { useEffect } from 'react'
import Taro from '@tarojs/taro'
import { useRiderStore } from './store'

// 路由守卫
const AuthRoute = ({ children }: { children: React.ReactNode }) => {
  const { token } = useRiderStore()

  useEffect(() => {
    const pages = Taro.getCurrentPages()
    const currentPage = pages[pages.length - 1]
    const path = currentPage?.route

    // 不需要登录的页面
    const publicPaths = ['pages/login/index']

    if (!token && !publicPaths.some(p => path?.includes(p))) {
      Taro.redirectTo({ url: '/pages/login/index' })
    }
  }, [token])

  return <>{children}</>
}

export default function App(props: any) {
  return (
    <AuthRoute>
      {props.children}
    </AuthRoute>
  )
}
