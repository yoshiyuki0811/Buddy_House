import { useQuery } from '@tanstack/react-query';
import { customerAPI } from '../../services/api';
import { Users, Mail, Phone, MapPin } from 'lucide-react';

export default function CustomerManagement() {
  const { data: customers, isLoading } = useQuery({
    queryKey: ['allCustomers'],
    queryFn: () => customerAPI.getList().then((r) => r.data),
  });

  if (isLoading) return (
    <div className="flex items-center justify-center py-24">
      <div className="w-6 h-6 rounded-full border-2 border-sky-400 border-t-transparent animate-spin" />
    </div>
  );

  return (
    <div className="space-y-6">

      {/* Header */}
      <div>
        <p className="text-xs font-mono text-zinc-500 mb-1">{customers?.length ?? 0} 名登録中</p>
        <h1 className="text-2xl font-bold text-zinc-100">顧客管理</h1>
      </div>

      {!customers || customers.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <Users className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm">顧客データがありません</p>
        </div>
      ) : (
        <div className="space-y-3">
          {customers.map((c: any) => (
            <div
              key={c.id}
              className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
            >
              <div className="flex items-start justify-between">
                <div className="flex items-center gap-4">
                  <div className="w-10 h-10 rounded-lg bg-sky-500/10 border border-sky-400/20 flex items-center justify-center text-sky-400 font-bold text-sm shrink-0">
                    {c.name?.charAt(0)}
                  </div>
                  <div>
                    <p className="text-sm font-semibold text-zinc-100">{c.name}</p>
                    <p className="text-xs font-mono text-zinc-500 mt-0.5">ID: {c.id}</p>
                  </div>
                </div>
              </div>

              <div className="mt-4 grid grid-cols-3 gap-3">
                {[
                  { icon: Mail,  value: c.email },
                  { icon: Phone, value: c.phone },
                  { icon: MapPin, value: c.address },
                ].map(({ icon: Icon, value }) => (
                  <div key={Icon.name} className="flex items-start gap-2">
                    <Icon className="w-3.5 h-3.5 text-zinc-500 mt-0.5 shrink-0" />
                    <p className="text-xs text-zinc-400 truncate">{value ?? '–'}</p>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      )}

    </div>
  );
}
