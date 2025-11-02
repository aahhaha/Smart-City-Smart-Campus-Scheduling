import pandas as pd
import matplotlib.pyplot as plt
import re

with open("metrics_log.txt", encoding="utf-8") as f:
    lines = f.readlines()

data = []
for line in lines:
    parts = line.strip().split('|')
    row = {}
    for p in parts:
        p = p.strip()
        if '=' in p:
            key, val = p.split('=', 1)
            key = key.strip().replace(':', '')
            m = re.search(r"(\d+)", val)
            row[key] = int(m.group(1)) if m else None
        elif p.startswith('data/'):
            row['file'] = p
    data.append(row)

df = pd.DataFrame(data)

df = df[['file','V','E','SCC','Tarjan','DAG','SP','total','CritPath']].sort_values('V')

print(df)

plt.figure(figsize=(10,6))
plt.plot(df['V'], df['total'], 'k-o', label='Total time')
plt.plot(df['V'], df['Tarjan'], 'r-o', label='TarjanSCC')
plt.plot(df['V'], df['DAG'], 'g-o', label='Condensation+Topo')
plt.plot(df['V'], df['SP'], 'b-o', label='DAG-SP')

plt.title("Execution Time vs Graph Size", fontsize=14)
plt.xlabel("Vertices (|V|)")
plt.ylabel("Time (×10⁶ ns)")
plt.legend()
plt.grid(True, linestyle="--", alpha=0.6)
plt.tight_layout()
plt.show()

plt.figure(figsize=(8,5))
plt.plot(df['V'], df['CritPath'], 'm-o', label='Critical Path Length')
plt.title("Critical Path vs Graph Size", fontsize=14)
plt.xlabel("Vertices (|V|)")
plt.ylabel("Critical Path Length")
plt.grid(True, linestyle='--', alpha=0.6)
plt.legend()
plt.tight_layout()
plt.show()
