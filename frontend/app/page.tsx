/**
 * @file app/page.tsx
 * @description Punto de entrada raíz de FinanziApp. Redirige al inicio de sesión.
 */

import { redirect } from 'next/navigation';

export default function RootPage() {
  // Redirecciona automáticamente al login impecable
  redirect('/login');
}