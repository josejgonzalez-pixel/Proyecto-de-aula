import type { Metadata } from 'next'
import { Geist, Geist_Mono } from 'next/font/google'
import './globals.css'
import {AuthProvider} from "./context/AuthContext";
import Chatbot from "@/components/Chatbot";

const _geist = Geist({ subsets: ["latin"] });
const _geistMono = Geist_Mono({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: 'FinanziApp - Gestión Financiera Personal',
  description: 'Aplicación de gestión financiera personal para controlar tus ingresos, gastos y presupuestos',
  generator: 'v0.app',
  icons: {
    icon: '/logo.png',
  },
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="es">
      <body>
        <AuthProvider>
          {children}
          <Chatbot />
        </AuthProvider>
      </body>
    </html>
  );
}
