import { create } from 'zustand'
import { persist } from 'zustand/middleware'

// 用户信息
interface UserInfo {
  id: number
  phone: string
  nickname: string
  avatar: string
}

// 全局状态
interface AppState {
  userInfo: UserInfo | null
  token: string | null
  location: {
    latitude: number
    longitude: number
    address: string
  } | null
  cart: {
    merchantId: number
    items: Array<{
      productId: number
      name: string
      price: number
      quantity: number
      spec?: string
    }>
  }[]
  
  // Actions
  setUserInfo: (user: UserInfo) => void
  setToken: (token: string) => void
  logout: () => void
  setLocation: (location: AppState['location']) => void
  addToCart: (merchantId: number, item: AppState['cart'][0]['items'][0]) => void
  removeFromCart: (merchantId: number, productId: number) => void
  clearCart: (merchantId: number) => void
}

export const useAppStore = create<AppState>()(
  persist(
    (set) => ({
      userInfo: null,
      token: null,
      location: null,
      cart: [],

      setUserInfo: (userInfo) => set({ userInfo }),
      setToken: (token) => set({ token }),
      logout: () => set({ userInfo: null, token: null }),
      setLocation: (location) => set({ location }),

      addToCart: (merchantId, item) => set((state) => {
        const existingMerchant = state.cart.find(c => c.merchantId === merchantId)
        if (existingMerchant) {
          const existingItem = existingMerchant.items.find(i => i.productId === item.productId)
          if (existingItem) {
            existingItem.quantity += item.quantity
          } else {
            existingMerchant.items.push(item)
          }
          return { cart: [...state.cart] }
        } else {
          return { cart: [...state.cart, { merchantId, items: [item] }] }
        }
      }),

      removeFromCart: (merchantId, productId) => set((state) => {
        const merchant = state.cart.find(c => c.merchantId === merchantId)
        if (merchant) {
          merchant.items = merchant.items.filter(i => i.productId !== productId)
        }
        return { cart: [...state.cart] }
      }),

      clearCart: (merchantId) => set((state) => {
        return { cart: state.cart.filter(c => c.merchantId !== merchantId) }
      })
    }),
    {
      name: 'fair-food-storage',
      partialize: (state) => ({
        token: state.token,
        userInfo: state.userInfo
      })
    }
  )
)
