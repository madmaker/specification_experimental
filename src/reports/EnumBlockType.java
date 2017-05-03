package reports;

public enum EnumBlockType
{
	NONE {
		public String title(){
			return "Неопределённый тип раздела";
		}
	},
	DOCUMENTS {
		public String title(){
			return "Документация";
		}
	},
	COMPLEXES {
		public String title(){
			return "Комплексы";
		}
	},
	ASSEMBLIES {
		public String title(){
			return "Сборочные единицы";
		}
	},
	DETAILS {
		public String title(){
			return "Детали";
		}
	},
	STANDARDS {
		public String title(){
			return "Стандартные изделия";
		}
	},
	OTHERS {
		public String title(){
			return "Прочие изделия";
		}
	},
	MATERIALS {
		public String title(){
			return "Материалы";
		}
	},
	KITS {
		public String title(){
			return "Комплекты";
		}
	};
	
	public abstract String title();
}
