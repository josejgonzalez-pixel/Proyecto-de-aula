/**
 * @file app/(pages)/configuracion/page.tsx
 * @description Página de configuración (desktop sidebar link)
 */

import { redirect } from 'next/navigation';

export default function ConfiguracionPage() {
  redirect('/ajustes');
}
